/*
package com.rew3.common.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.billing.sales.invoice.model.AbstractDTO;
import com.rew3.billing.sales.invoice.model.InvoiceItem;
import com.rew3.payment.model.BillingAccount;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.commission.transaction.model.RmsTransaction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Parser;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public abstract class RestAction extends BaseAction {

    private static final long serialVersionUID = 8653391256322997267L;

    public Map<String, Object> getJSONRequest(HttpServletRequest req) {
        System.out.println(req.getServletPath());
        StringBuilder buffer = new StringBuilder();
        String json = "";
        Map<String, Object> map = new HashMap<>();
        BufferedReader reader;
        try {
            reader = req.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            json = buffer.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (json != null) {
            try {

                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

    public ArrayList<HashMap<String, Object>> getJSONRequestForBulk(HttpServletRequest req) {
        System.out.println(req.getServletPath());
        StringBuilder buffer = new StringBuilder();
        String json = "";
        ArrayList<HashMap<String, Object>> maps = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = req.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            json = buffer.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (json != null) {
            try {
                // List<Map<String, Object>> maps = new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
                maps = mapper.readValue(json, new TypeReference<List<HashMap<String, Object>>>() {


                });
                System.out.println(maps);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return maps;
    }

    public Map<String, Object> getQueryRequest(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String[]> query = req.getParameterMap();
        for (String key : query.keySet()) {
            String[] arr = query.get(key);
            if (arr.length >= 1) {
                map.put(key, arr[0]);
            }
        }
        return map;
    }

    public RestAction() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String method = req.getMethod();
        if (method.equals("GET") & !req.getServletPath().contains("/bulk")) {
            this.setRequest(getQueryRequest(req));
        } else if (req.getServletPath().contains("/bulk") && !req.getServletPath().contains("/bulk/remove") && (method.equals("PUT") | method.equals("POST"))) {
            this.setRequests(getJSONRequestForBulk(req));
        } else if (method.equals("DELETE")) {
            this.setRequest(getJSONRequest(req));

        } else {
            this.setRequest(getJSONRequest(req));
        }
    }


//    public String execute() {
//        return Action.SUCCESS;
//    }

    protected String setJsonResponseForCreate(Object c, Flags.EntityType type) {
        HashMap<String, Object> map = new HashMap<>();
        if (c != null) {
            map.put("result", SUCCESS);
            map.put("data", c);
            if (type == Flags.EntityType.BILLING_ACCOUNT) {
                BillingAccount account = (BillingAccount) c;
                map.put("accountingCode", account.getAccountingCode().getCode());
            }
            map.put("message", type.toString().toLowerCase() + " successfully added");
            setJsonResponse(Parser.convert(map));
            return SUCCESS;
        }
        return null;
    }

    protected String setJsonResponseForAdd(Object c, Flags.EntityType toBeAdded, Flags.EntityType addedTo) {
        HashMap<String, Object> map = new HashMap<>();
        if (c != null) {
            map.put("result", SUCCESS);
            map.put("data", c);

            map.put("message", toBeAdded.toString().toLowerCase() + " successfully added to " + addedTo.getString().toLowerCase());
            setJsonResponse(Parser.convert(map));
            return SUCCESS;
        }
        return null;
    }

    protected String setJsonResponseForRemove(Object c, Flags.EntityType toBeRemoved, Flags.EntityType removedFrom) {
        HashMap<String, Object> map = new HashMap<>();
        if (c != null) {
            map.put("result", SUCCESS);
            map.put("data", c);

            map.put("message", toBeRemoved.toString().toLowerCase() + " successfully removed from  " + removedFrom.getString().toLowerCase());
            setJsonResponse(Parser.convert(map));
            return SUCCESS;
        }
        return null;
    }


    protected String setJsonResponseForUpdate(Object c) {
        HashMap<String, Object> map = new HashMap<>();
        if (c != null & !(c instanceof Exception)) {
            map.put("result", SUCCESS);
            map.put("data", c);
            map.put("message", "Successfully updated");
            setJsonResponse(Parser.convert(map));
            return SUCCESS;
        } else {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Content-Type", "application/json");
            if (c instanceof NotFoundException) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else if (c instanceof CommandException) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }

            map.put("result", ERROR);
            map.put("log", APILogger.getList());
            APILogger.clear();

            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            setJsonResponse(Parser.convert(map));
            return ERROR;
        }
    }

    protected String setJsonResponseForDelete(Object c) {
        HashMap<String, Object> map = new HashMap<>();
        AbstractDTO abstractDTO = null;

        abstractDTO = (AbstractDTO) c;


        if (c != null) {
            map.put("result", SUCCESS);
            map.put("id", abstractDTO.get_id());
            map.put("data", c);


            map.put("message", " " + "successfully deleted");
            setJsonResponse(Parser.convert(map));

        }
        return SUCCESS;
    }


    protected String setJsonResponseForGet(Query q, List<Object> val) {

        if (q.has("offset")) {
            q.getQuery().remove("offset");
        }
        if (q.has("offset")) {
            q.getQuery().remove("offset");
        }

        Long total = Long.valueOf(val.size());

        HashMap<String, Object> map = new HashMap<>();
        if (q.validate()) {
            map.put("data", Parser.convert(val));
            setJsonResponse(map);
            Long limit = Long.valueOf(PaginationParams.LIMIT);
            if (q.has("limit")) {
                limit = Parser.convertObjectToLong(q.get("limit"));
            }


            long pagesCount = total / limit;

            if (total % limit != 0) {
                pagesCount = pagesCount + 1;
            }


            if (q.has("page_number")) {
                map.put("page_number", Parser.convertObjectToLong(q.get("page_number")));
            }


            map.put("limit", limit);
            map.put("pages_count", pagesCount);
            map.put("total_count", total);
            return SUCCESS;
        } else {
            map.put("result", ERROR);
            map.put("log", APILogger.getList());
            APILogger.clear();
            return ERROR;
        }


    }

    protected String setJsonResponseForGet(HashMap q, List<Object> val, Long total, Flags.EntityType entityType) {


        HashMap<String, Object> map = new HashMap<>();

        map.put("data", Parser.convert(val));
        setJsonResponse(map);
        Long limit = Long.valueOf(PaginationParams.LIMIT);
        Long offset = Long.valueOf(PaginationParams.OFFSET);
        Long page_total = total;

        if (q.get("limit") != null) {
            limit = Parser.convertObjectToLong(q.get("limit"));
        }
        if (q.get("offset") != null) {
            offset = Parser.convertObjectToLong(q.get("offset"));
            map.put("offset", offset);
            page_total = total - offset;

        }
        long pagesCount = page_total / limit;

        if (page_total % limit != 0 & val.size() != 0) {
            pagesCount = pagesCount + 1;
        }
        if (q.get("page_number") != null) {
            map.put("page_number", Parser.convertObjectToLong(q.get("page_number")));
        }
        map.put("limit", limit);
        map.put("pages_count", pagesCount);
        map.put("total_count", total);
        if (q.get("aggregate") != null) {
            Map map1 = new HashMap();
            map1.put("total_amount", Parser.convertObjectToDouble(q.get("aggregate")));
            map.put("aggregate", map1);
        }
        return SUCCESS;


    }

    protected String setJsonResponseForGet(Query q, Set<Object> val, Long total) {


        HashMap<String, Object> map = new HashMap<>();
        if (q.validate()) {
            map.put("data", Parser.convert(val));
            setJsonResponse(map);
            Long limit = Long.valueOf(PaginationParams.LIMIT);
            Long offset = Long.valueOf(PaginationParams.OFFSET);
            if (q.has("limit")) {
                limit = Parser.convertObjectToLong(q.get("limit"));
            }


            long pagesCount = total / limit;

            if (total % limit != 0) {
                pagesCount = pagesCount + 1;
            }


            if (q.has("page_number")) {
                map.put("page_number", Parser.convertObjectToLong(q.get("page_number")));
            }


            map.put("limit", limit);
            map.put("pages_count", pagesCount);
            map.put("total_count", total);
            return SUCCESS;
        } else {
            map.put("result", ERROR);
            map.put("log", APILogger.getList());
            APILogger.clear();
            return ERROR;
        }


    }

    protected String setJsonResponseForGet(List<Object> val) {
        HashMap<String, Object> map = new HashMap<>();
        if (val != null) {
            map.put("data", Parser.convert(val));
            setJsonResponse(map);
            //  getData().put("logs", APILogger.getList());
            //setJsonResponse();
            return SUCCESS;
        } else {

            map.put("result", ERROR);
            map.put("log", APILogger.getList());
            APILogger.clear();
            return ERROR;
        }


    }

    protected String setJsonResponseForGetById(Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        if (obj != null) {
            map.put("data", Parser.convert(obj));
            setJsonResponse(map);
            return SUCCESS;
        }
        return null;
    }

    protected void setJsonResponseForBulkRemove(String bta) {
        HashMap<String, Object> map = new HashMap<>();


        if (bta != null) {
            map.put("result", SUCCESS);
            map.put("message", "Entities successfully deleted");

            setJsonResponse(map);
        } else {
            map.put("result", ERROR);

            map.put("log", APILogger.getList());

            setJsonResponse(map);

            APILogger.clear();
        }
    }


    protected void setJsonResponseForBulkUpdate(List<Object> objs) {
        HashMap<String, Object> map = new HashMap<>();
        if (objs != null && objs.size() > 0) {
            map.put("result", SUCCESS);
            map.put("id", Parser.convertToIdsList(objs));
            map.put("data", Parser.convert(objs));
            map.put("message", "Bulk record successfully updated");
            setJsonResponse(map);
        } else {
            map.put("result", ERROR);

            map.put("log", APILogger.getList());

            setJsonResponse(map);

            APILogger.clear();
        }
    }

    protected void setJsonResponseForBulkCreation(List<Object> objs) {

        HashMap<String, Object> map = new HashMap<>();
        if (objs != null && objs.size() > 0) {
            map.put("result", SUCCESS);
            map.put("id", Parser.convertToIdsList(objs));
            map.put("data", Parser.convert(objs));
            map.put("message", "Bulk record successfully added");

            setJsonResponse(map);
        } else {
            map.put("result", ERROR);

            map.put("log", APILogger.getList());

            setJsonResponse(map);

            APILogger.clear();
        }
    }

    protected void setJsonResponseForBulkRemove(List<Object> objs) {

        HashMap<String, Object> map = new HashMap<>();
        if (objs != null && objs.size() > 0) {
            map.put("result", SUCCESS);
            map.put("id", Parser.convertToIdsList(objs));
            map.put("data", Parser.convert(objs));
            map.put("message", "Bulk record successfully deleted");

            setJsonResponse(map);
        } else {
            map.put("result", ERROR);

            map.put("log", APILogger.getList());

            setJsonResponse(map);

            APILogger.clear();
        }
    }


    protected String setStandardJsonResponse(List<Object> bta, Object msg) {
        HashMap<String, Object> map = new HashMap<>();
        if (bta != null) {
            map.put("result", SUCCESS);
            map.put("ids", bta);
            map.put("message", msg);

            setJsonResponse(map);
            return SUCCESS;
        } else {
            map.put("result", ERROR);

            map.put("log", APILogger.getList());

            setJsonResponse(map);

            APILogger.clear();
            return ERROR;
        }
    }


    protected String setErrorResponse(Exception ex) {
        HashMap<String, Object> map = new HashMap<>();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Content-Type", "application/json");
        if (ex instanceof NotFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else if (ex instanceof CommandException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ex.printStackTrace();
        }
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("result", ERROR);
        map.put("log", APILogger.getList());
        APILogger.clear();

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        setJsonResponse(map);
        return ERROR;
    }


}






*/

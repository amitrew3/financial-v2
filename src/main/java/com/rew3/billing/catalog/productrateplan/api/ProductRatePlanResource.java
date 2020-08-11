/*
package com.rew3.billing.catalog.productrateplan.api;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.productcategory.ProductCategoryQueryHandler;
import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.billing.catalog.productrateplan.ProductRatePlanChargeQueryHandler;
import com.rew3.billing.catalog.productrateplan.command.*;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlanCharge;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.Parser;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.rew3.billing.catalog.productrateplan.ProductRatePlanQueryHandler;
import com.rew3.common.api.RestAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogger;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class ProductRatePlanResource extends RestAction {

    @Action(value = "sales/rateplan", results = {@Result(type = "json")})
    public String actionRatePlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getRatePlan();
        } else if (httpRequest.getMethod().equals("POST")) {
            createRatePlan();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateRatePlan();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }
        return response;
    }

    @Action(value = "sales/rateplancharge", results = {@Result(type = "json")})
    public String actionRatePlanCharge() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getRatePlanCharge();
        } else if (httpRequest.getMethod().equals("POST")) {
            createRatePlanCharge();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateRatePlanCharge();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }
        return response;
    }

    private void updateRatePlanCharge() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateProductRatePlanCharge command = new UpdateProductRatePlanCharge(requestData);
            CommandRegister.getInstance().process(command);
            ProductRatePlanCharge c = (ProductRatePlanCharge) command.getObject();
            setJsonResponseForUpdate(c);

        } catch (Exception e) {
            setErrorResponse(e);
        }


    }

    private void createRatePlanCharge() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            CreateProductRatePlanCharge command = new CreateProductRatePlanCharge(requestData);
            CommandRegister.getInstance().process(command);
            ProductRatePlanCharge c = (ProductRatePlanCharge) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.PRODUCT_RATE_PLAN_Charge);

        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String createRatePlan() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateProductRatePlan command = new CreateProductRatePlan(requestData);
            CommandRegister.getInstance().process(command);
            ProductRatePlan ratePlan = (ProductRatePlan) command.getObject();
            //c.getCharges();
            setJsonResponseForCreate(ratePlan, Flags.EntityType.PRODUCT_RATE_PLAN);

        } catch (Exception ex) {
            setErrorResponse(ex);

        }


        return responseStatus;
    }

    public String updateRatePlan() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateProductRatePlan command = new UpdateProductRatePlan(requestData);
            CommandRegister.getInstance().process(command);
            ProductRatePlan pf = (ProductRatePlan) command.getObject();
            setJsonResponseForUpdate(pf);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;

    }


    public String getRatePlan() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/catalog/rateplan/get");

        List<Object> features = (new ProductRatePlanQueryHandler()).get(q);

        setJsonResponseForGet(q, features);

        return responseStatus;
    }

    public String getRatePlanCharge() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/catalog/rateplancharge/get");

        List<Object> charges = (new ProductRatePlanChargeQueryHandler()).get(q);

        setJsonResponseForGet(q, charges);

        return responseStatus;
    }

    public void deleteRatePlan(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        try {
            DeleteProductRatePlan command = new DeleteProductRatePlan(map);
            CommandRegister.getInstance().process(command);
            ProductRatePlan ratePlan = (ProductRatePlan) command.getObject();
            setJsonResponseForDelete(ratePlan);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String actionBulkRatePlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkProductRatePlan();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})
    public String actionRemoveBulkRatePlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkProductRatePlan();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkRatePlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkProductRatePlan();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String createBulkProductRatePlan() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkProductRatePlan command = new CreateBulkProductRatePlan(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }

    public String updateBulkProductRatePlan() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkProductRatePlan command = new UpdateBulkProductRatePlan(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkProductRatePlan() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkProductRatePlan command = new DeleteBulkProductRatePlan(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();

        setJsonResponseForBulkRemove(bta);

        return responseStatus;
    }


    public String actionRatePlanById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getRatePlanById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteRatePlan(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }


    public String actionRatePlanChargeById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getRatePlanChargeById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteRatePlanCharge(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    public String getRatePlanById(String id) throws CommandException, NotFoundException {

        ProductRatePlan ratePlan = (ProductRatePlan) (new ProductRatePlanQueryHandler()).getById(id);


        return setJsonResponseForGetById(ratePlan);
    }

    public void getRatePlanChargeById(String id) {

        try {
            ProductRatePlanCharge normalUser = (ProductRatePlanCharge) (new ProductRatePlanChargeQueryHandler()).getById(id);
            setJsonResponseForGetById(normalUser);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }

    private void deleteRatePlanCharge(String id) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteProductRatePlanCharge command = new DeleteProductRatePlanCharge(map);
            CommandRegister.getInstance().process(command);
            ProductRatePlanCharge rpc = (ProductRatePlanCharge) command.getObject();
            setJsonResponseForDelete(rpc);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }


}
*/

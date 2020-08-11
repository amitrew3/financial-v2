/*
package com.rew3.finance.accountingcode.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.commission.commissionplan.CommissionPlanQueryHandler;
import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.commission.commissionplan.model.CommissionPlanDTO;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.finance.accountingcode.AccountingClassQueryHandler;
import com.rew3.finance.accountingcode.AccountingCodeQueryHandler;
import com.rew3.finance.accountingcode.SubAccountingHeadQueryHandler;
import com.rew3.finance.accountingcode.command.*;
import com.rew3.finance.accountingcode.model.AccountingClass;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingcode.model.AccountingCodeDTO;
import com.rew3.finance.accountingcode.model.SubAccountingHead;
import com.rew3.finance.service.AccountingService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1/accounting")
public class AccountingCodeResource extends RestAction {

    AccountingService service = new AccountingService();

    public String actionCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getAccountingCode(requestData);
                    break;

                }
                case "POST": {
                    createAccountingCode(requestData);
                    break;

                }
                case "PUT": {
                    updateAccountingCode(requestData);
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorResponse(ex);
        }
        return response;
    }

    public String createAccountingCode(HashMap<String, Object> requestData) throws Exception {


        Transaction trx = HibernateUtils.startTransaction();
        AccountingCode plan = null;

        try {
            plan = service.createUpdateAccountingCode(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        AccountingCodeDTO dto = service.getAccountingCodeById(plan.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);
        return SUCCESS;
    }

    public void updateAccountingCode(HashMap<String, Object> requestData) throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        AccountingCode plan = null;

        try {
            plan = service.createUpdateAccountingCode(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            throw e;
//            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
        AccountingCodeDTO dto = service.getAccountingCodeById(plan.get_id());
        setJsonResponseForUpdate(dto);

    }

    public void getAccountingCode(HashMap<String, Object> requestData) throws CommandException, NotFoundException, JsonProcessingException {
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> accountingCodes = service.getAccountingCode(requestData);

        Query original = new Query(requestData);

        Long totalCount = new CommissionPlanQueryHandler().count(original);

        setJsonResponseForGet(requestMap, accountingCodes, totalCount, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);

    }

    @Action(value = "class", results = {@Result(type = "json")})
    public String actionAccountingClass() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getAccountingClass();
        } else if (httpRequest.getMethod().equals("POST")) {
            createAccountingClass();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateAccountingClass();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createAccountingClass() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            CreateAccountingClass command = new CreateAccountingClass(requestData);
            CommandRegister.getInstance().process(command);
            AccountingClass c = (AccountingClass) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.ACCOUNTING_CLASS);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    public String updateAccountingClass() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateAccountingClass command = new UpdateAccountingClass(requestData);
            CommandRegister.getInstance().process(command);
            AccountingClass aclass = (AccountingClass) command.getObject();

            setJsonResponseForUpdate(aclass);

        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;


    }

    public String getAccountingClass() throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "finance/accountingclass/get");

        List<Object> aclassList = (new AccountingClassQueryHandler()).get(q);

        setJsonResponseForGet(q, aclassList);


        return responseStatus;
    }

    @Action(value = "heads", results = {@Result(type = "json")})
    public String actionAccountingHeads() {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getAccountingHeads();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String getAccountingHeads() {


        List<HashMap<String, String>> heads = new ArrayList<HashMap<String, String>>();

        for (Flags.AccountingHead h : Flags.AccountingHead.values()) {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("id", h.getId().toString());
            hash.put("code", h.getCode().toString());
            hash.put("title", h.getString());
            heads.add(hash);
        }

        // setJsonResponseForGet(heads);

        return SUCCESS;
    }

    public String actionBulkAccountingCode() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkAccountingCode();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})
    public String actionRemoveBulkAccountingCode() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkAccountingCode();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkAccountingCode() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkAccountingCode();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;

    }


    public String actionBulkAccountingClass() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkAccountingClass();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionRemoveBulkAccountingClass() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkAccountingClass();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkAccountingClass() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkAccountingClass();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createBulkAccountingCode() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkAccountingCode command = new CreateBulkAccountingCode(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }

    public String updateBulkAccountingCode() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkAccountingCode command = new UpdateBulkAccountingCode(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();
        setJsonResponseForUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkAccountingCode() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkAccountingCode command = new DeleteBulkAccountingCode(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();

        setJsonResponseForDelete(bta);

        return responseStatus;
    }


    public String createBulkAccountingClass() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkAccountingClass command = new CreateBulkAccountingClass(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }

    public String updateBulkAccountingClass() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkAccountingClass command = new UpdateBulkAccountingClass(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();
        setJsonResponseForUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkAccountingClass() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkAccountingClass command = new DeleteBulkAccountingClass(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();

        setJsonResponseForBulkRemove(bta);

        return responseStatus;
    }

    public void deleteAccountingClass(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteAccountingCode command = new DeleteAccountingCode(map);
            CommandRegister.getInstance().process(command);
            AccountingCode code = (AccountingCode) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }


    public String deleteAccountingCode() throws Exception {
        String responseStatus = SUCCESS;
        System.out.println("Delete TieredAcp");
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteAccountingCode command = new DeleteAccountingCode(requestData);
        CommandRegister.getInstance().process(command);
        AccountingCode c = (AccountingCode) command.getObject();

        setJsonResponseForDelete(c.toString());

        return responseStatus;
    }

    public String actionAccountingCodeById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getAccountingCodeById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteAccountingCode(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private String getAccountingCodeById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        AccountingCode pro = (AccountingCode) (new AccountingCodeQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }

    private void deleteAccountingCode(String id) throws CommandException, NotFoundException, JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteAccountingCode command = new DeleteAccountingCode(map);
            CommandRegister.getInstance().process(command);
            AccountingCode code = (AccountingCode) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String actionAccountClassById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getAccountingClassById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteAccountingClass(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private String getAccountingClassById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        AccountingClass pro = (AccountingClass) (new AccountingClassQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }


    @Action(value = "code", results = {@Result(type = "json")})
    public String actionSubAccountingHead() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getSubAccountingHead();
        } else if (httpRequest.getMethod().equals("POST")) {
            createSubAccountingHead();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateSubAccountingHead();
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionSubAccountingHeadById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getSubAccountingHeadById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteSubAccountingHead(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }


    public String createSubAccountingHead() throws Exception {


        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateSubAccountingHead command = new CreateSubAccountingHead(requestData);
        CommandRegister.getInstance().process(command);
        SubAccountingHead c = (SubAccountingHead) command.getObject();

        if (c != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("result", SUCCESS);
            map.put("id", c.get_id());
            map.put("message", "Sub Accounting Head successfully added");
            setJsonResponse(map);
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("result", ERROR);
            map.put("log", APILogger.getList());
            setJsonResponse(map);
        }

        return responseStatus;
    }

    public String updateSubAccountingHead() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();


        UpdateSubAccountingHead command = new UpdateSubAccountingHead(requestData);
        CommandRegister.getInstance().process(command);
        SubAccountingHead acode = (SubAccountingHead) command.getObject();


        setJsonResponseForUpdate(acode);

        return responseStatus;
    }

    public String getSubAccountingHead() throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "finance/accountingcode/get");

        List<Object> acodeList = (new SubAccountingHeadQueryHandler()).get(q);

        setJsonResponseForGet(q, acodeList);

        return responseStatus;
    }


    public void deleteSubAccountingHead(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteSubAccountingHead command = new DeleteSubAccountingHead(map);
            CommandRegister.getInstance().process(command);
            SubAccountingHead journal = (SubAccountingHead) command.getObject();
            setJsonResponseForDelete(journal);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    private String getSubAccountingHeadById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        SubAccountingHead pro = (SubAccountingHead) new SubAccountingHeadQueryHandler().getById(id);
        return setJsonResponseForGetById(pro);
    }

}
*/

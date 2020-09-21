/*
package com.rew3.finance.accountingperiod.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.finance.accountingperiod.AccountingPeriodQueryHandler;
import com.rew3.finance.accountingperiod.AccountingPeriodRequestQueryHandler;
import com.rew3.finance.accountingperiod.command.*;
import com.rew3.finance.accountingperiod.model.AccountingPeriod;
import com.rew3.finance.accountingperiod.model.AccountingPeriodRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
public class AccountPeriodResource extends RestAction {

    @Action(value = "normaluser", results = {@Result(type = "json")})
    public String actionAccountingPeriod() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createAccountingPeriod();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateAccountingPeriod();
        } else if (httpRequest.getMethod().equals("GET")) {
            getAccountingPeriod();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

   */
/* public String getHero() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);
        List<Object> normalUsers = (new AccountingPeriodQueryHandler()).get(q);
        Long total = new AccountingPeriodQueryHandler().count();
        // System.out.println(count);

        setJsonResponseForGet(q, normalUsers, total);

        return responseStatus;
    }*//*


    public String createAccountingPeriod() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateAccountingPeriod command = new CreateAccountingPeriod(requestData);
        CommandRegister.getInstance().process(command);
        AccountingPeriod c = (AccountingPeriod) command.getObject();

        setJsonResponseForCreate(c, Flags.EntityType.ACCOUNTING_PERIOD);

        return responseStatus;
    }

    public String updateAccountingPeriod() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateAccountingPeriod command = new UpdateAccountingPeriod(requestData);
        CommandRegister.getInstance().process(command);
        AccountingPeriod ap = (AccountingPeriod) command.getObject();

        setJsonResponseForUpdate(ap.toString());

        return responseStatus;
    }

    public String closeAccountingPeriod() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CloseAccountingPeriod command = new CloseAccountingPeriod(requestData);
        CommandRegister.getInstance().process(command);
        AccountingPeriod ap = (AccountingPeriod) command.getObject();
        return responseStatus;

    }


    public String reopenAccountingPeriod() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        ReopenAccountingPeriod command = new ReopenAccountingPeriod(requestData);
        CommandRegister.getInstance().process(command);
        AccountingPeriod ap = (AccountingPeriod) command.getObject();

       */
/* if (ap != null) {
            responseStatus = SUCCESS;
            setJsonResponse("Accounting period reopened successfully.");
            getData().put("accountingPeriodId", ap.get_id().toString());
        } else {
            setError("Accounting period reopen failed.");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    @Action(value = "period/request", results = {@Result(type = "json")})
    public String actionAccountingPeriodRequest() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createAccountingPeriodRequest();
        }
        if (httpRequest.getMethod().equals("GET")) {
            getAccountingPeriodRequest();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createAccountingPeriodRequest() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateAccountingPeriodRequest command = new CreateAccountingPeriodRequest(requestData);
        CommandRegister.getInstance().process(command);
        String c = (String) command.getObject();

        setJsonResponseForCreate(c, Flags.EntityType.ACCOUNTING_PERIOD_REQUEST);

        return responseStatus;
    }

    @Action(value = "period/{id}/accept", results = {@Result(type = "json")})
    public String actionAcceptAccountingPeriodRequest() throws Exception {
        String responseStatus = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();

        if (httpRequest.getMethod().equals("POST")) {
            responseStatus = acceptAccountingPeriodRequest();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return responseStatus;
    }

    public String acceptAccountingPeriodRequest() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        AcceptAccountingPeriodRequest command = new AcceptAccountingPeriodRequest(requestData);
        CommandRegister.getInstance().process(command);
        AccountingPeriodRequest apr = (AccountingPeriodRequest) command.getObject();

       */
/* if (apr != null) {
            responseStatus = SUCCESS;
            setJsonResponse("Accounting Period request of id " + apr.get_id() + " has been successfully accepted.");
            getData().put("accountingPeriodRequest", apr.get_id());
        } else {
            responseStatus = ERROR;
            setError("Accepting request failed.");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    public String getAccountingPeriod() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "finance/accountingperiod/get");

        List<Object> accountingPeriods = new AccountingPeriodQueryHandler().get(q);

        setJsonResponseForGet(q, accountingPeriods);
        System.out.println(responseStatus);
        return responseStatus;
    }

    public String getAccountingPeriodRequest() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "finance/accountingperiodrequest/get");

        List<Object> accountingPeriodRequests = new AccountingPeriodQueryHandler().get(q);

        setJsonResponseForGet(q, accountingPeriodRequests);

        return responseStatus;
    }


    public void deleteAccountingPeriod(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteAccountingPeriod command = new DeleteAccountingPeriod(map);
            CommandRegister.getInstance().process(command);
            AccountingPeriod period = (AccountingPeriod) command.getObject();
            setJsonResponseForDelete(period);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }


    public String actionRemoveBulkAccountingPeriod() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkAccountingPeriod();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkAccountingPeriod() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkAccountingPeriod();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionBulkAccountingPeriod() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkAccountingPeriod();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String createBulkAccountingPeriod() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkAccountingPeriod command = new CreateBulkAccountingPeriod(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();
        setJsonResponseForCreate(bta, Flags.EntityType.ACCOUNTING_PERIOD);
        return responseStatus;
    }

    public String updateBulkAccountingPeriod() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkAccountingPeriod command = new UpdateBulkAccountingPeriod(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();
        setJsonResponseForUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkAccountingPeriod() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkAccountingPeriod command = new DeleteBulkAccountingPeriod(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();

        setJsonResponseForBulkRemove(bta);

        return responseStatus;
    }

    public String actionAccountingPeriodById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getAccountingPeriodById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteAccountingPeriod(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private String getAccountingPeriodById(String id) throws CommandException, NotFoundException {


        AccountingPeriod pro = (AccountingPeriod) (new AccountingPeriodQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }

    public String actionAccountingPeriodRequestById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getAccountingPeriodRequestById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteAccountingPeriodRequest(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private void deleteAccountingPeriodRequest(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteAccountingPeriodRequest command = new DeleteAccountingPeriodRequest(map);
            CommandRegister.getInstance().process(command);
            AccountingPeriodRequest journal = (AccountingPeriodRequest) command.getObject();
            setJsonResponseForDelete(journal);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    private String getAccountingPeriodRequestById(String id) throws CommandException, NotFoundException {
        AccountingPeriodRequest pro = (AccountingPeriodRequest) (new AccountingPeriodRequestQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }

}

*/

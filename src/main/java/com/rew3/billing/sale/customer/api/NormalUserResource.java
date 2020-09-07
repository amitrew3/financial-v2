/*
package com.rew3.billing.sales.customer.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.sales.customer.NormalUserQueryHandler;
import com.rew3.billing.sales.customer.PaymentOptionQueryHandler;
import com.rew3.billing.sales.invoice.PaymentTermQueryHandler;
import com.rew3.billing.sales.customer.command.*;
import com.rew3.billing.sales.customer.model.NormalUser;
import com.rew3.billing.sales.customer.model.PaymentOption;
import com.rew3.billing.sales.invoice.model.PaymentTerm;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
public class NormalUserResource extends RestAction {

    @Action(value = "normaluser", results = {@Result(type = "json")})
    public String actionNormalUser() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createNormalUser();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateNormalUser();

        } else if (httpRequest.getMethod().equals("GET")) {
            getNormalUser();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionNormalUserById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getNormalUserById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteNormalUser(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }


    @Action(value = "normalUser/paymentOptions", results = {@Result(type = "json")})
    public String actionNormalUserPaymentOptions() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createPaymentOption();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updatePaymentOption();
        } else if (httpRequest.getMethod().equals("GET")) {
            getPaymentOption();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String createNormalUser() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateNormalUser command = new CreateNormalUser(requestData);
            CommandRegister.getInstance().process(command);
            NormalUser c = (NormalUser) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.NORMALUSER);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }


    public String updateNormalUser() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateNormalUser command = new UpdateNormalUser(requestData);
            CommandRegister.getInstance().process(command);
            NormalUser nu = (NormalUser) command.getObject();
            setJsonResponseForUpdate(nu);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    public String getNormalUser() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);
        List<Object> normalUsers = (new NormalUserQueryHandler()).get(q);

        setJsonResponseForGet(q, normalUsers);

        return responseStatus;
    }


    public void deleteNormalUser(String id) throws CommandException, NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteNormalUser command = new DeleteNormalUser(map);
            CommandRegister.getInstance().process(command);
            NormalUser code = (NormalUser) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }

    }


    public String createPaymentOption() throws CommandException, NotFoundException, ServletException, JsonProcessingException {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            CreatePaymentOption command = new CreatePaymentOption(requestData);
            CommandRegister.getInstance().process(command);
            PaymentOption c = (PaymentOption) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.PAYMENT_OPTION);


        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }


    public String updatePaymentOption() throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            CreatePaymentOption command = new CreatePaymentOption(requestData);
            CommandRegister.getInstance().process(command);
            PaymentOption c = (PaymentOption) command.getObject();
            setJsonResponseForUpdate(c);

        } catch (Exception e) {
            setErrorResponse(e);
        }
        return SUCCESS;
    }

    public String getPaymentOption() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);

        List<Object> paymentOptions = (new PaymentOptionQueryHandler()).get(q);


        setJsonResponseForGet(q, paymentOptions);

        return responseStatus;
    }

    public void deletePaymentOption(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeletePaymentOption command = new DeletePaymentOption(map);
            CommandRegister.getInstance().process(command);
            PaymentOption code = (PaymentOption) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String actionBulkNormalUser() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkNormalUser();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})

    public String actionUpdateBulkNormalUser() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkNormalUser();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String actionRemoveBulkNormalUser() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {

            removeBulkNormalUser();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createBulkNormalUser() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkNormalUser command = new CreateBulkNormalUser(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getListObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }


    public String updateBulkNormalUser() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkNormalUser command = new UpdateBulkNormalUser(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();

        setJsonResponseForBulkUpdate(bta);
        return responseStatus;
    }


    public String removeBulkNormalUser() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkNormalUser command = new DeleteBulkNormalUser(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();
        setJsonResponseForBulkRemove(bta);
        return responseStatus;
    }


    public void getNormalUserById(String id) {

        try {
            NormalUser normalUser = (NormalUser) (new NormalUserQueryHandler()).getById(id);
            setJsonResponseForGetById(normalUser);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }


    private void getPaymentTermById(String id) throws CommandException, NotFoundException {
        try {
            PaymentTerm pro = (PaymentTerm) (new PaymentTermQueryHandler()).getById(id);
            setJsonResponseForGetById(pro);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }
    }


    public String actionNormalUserPaymentOptionsById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getNormalUserPaymentOptionsById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deletePaymentOption(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;

    }

    private void getNormalUserPaymentOptionsById(String id) throws CommandException, NotFoundException {
        try {
            PaymentOption pro = (PaymentOption) (new PaymentOptionQueryHandler()).getById(id);
            setJsonResponseForGetById(pro);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }
    }

}


*/

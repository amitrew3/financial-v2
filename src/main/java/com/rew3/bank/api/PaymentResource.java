/*
package com.rew3.payment.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.payment.*;
import com.rew3.payment.invoicepayment.command.*;
import com.rew3.payment.invoicepayment.command.DepositItemQueryHandler;
import com.rew3.payment.invoicepayment.model.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.rew3.common.api.RestAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogger;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class PaymentResource extends RestAction {

    private static final long serialVersionUID = 1290L;

    @Action(value = "payment/receipt", results = {@Result(type = "json")})
    public String actionReceipt() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getReceipt();
        } else if (httpRequest.getMethod().equals("POST")) {
            createReceipt();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createReceipt() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreatePaymentReceipt command = new CreatePaymentReceipt(requestData);
        CommandRegister.getInstance().process(command);
        PaymentReceipt r = (PaymentReceipt) command.getObject();

        setJsonResponseForCreate(r, Flags.EntityType.PAYMENT_RECEIPT);

        return responseStatus;
    }


    public String getReceipt() throws CommandException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/payment/receipt/get");

        List<Object> payments = (new PaymentReceiptQueryHandler()).get(q);

        // System.out.println(count);

        setJsonResponseForGet(q, payments);


        return responseStatus;
    }

    @Action(value = "payment/account", results = {@Result(type = "json")})
    public String actionBillingAccount() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getBillingAccount();
        } else if (httpRequest.getMethod().equals("POST")) {
            createBillingAccount();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateBillingAccount();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createBillingAccount() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateBillingAccount command = new CreateBillingAccount(requestData);
        CommandRegister.getInstance().process(command);
        BillingAccount ba = (BillingAccount) command.getObject();

        setJsonResponseForCreate(ba, Flags.EntityType.BILLING_ACCOUNT);

        return responseStatus;
    }

    public String updateBillingAccount() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateBillingAccount command = new UpdateBillingAccount(requestData);
        CommandRegister.getInstance().process(command);
        BillingAccount ba = (BillingAccount) command.getObject();

        setJsonResponseForUpdate(ba.get_id().toString());

        return responseStatus;
    }

    public void deleteBillingAccount(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteBillingAccount command = new DeleteBillingAccount(map);
            CommandRegister.getInstance().process(command);
            BillingAccount code = (BillingAccount) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String getBillingAccount() throws CommandException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/payment/account/get");

        List<Object> accounts = (new BillingAccountQueryHandler()).get(q);


        // System.out.println(count);

        setJsonResponseForGet(q, accounts);

        return responseStatus;
    }

    @Action(value = "payment/account/transaction", results = {@Result(type = "json")})
    public String actionBankTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getBankTransaction();
        } else if (httpRequest.getMethod().equals("POST")) {
            createBankTransaction();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateBankTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionBulkBankTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkBankTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionRemoveBulkBankTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkBankTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkBankTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkBankTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})
    public String actionRemoveBulkBillingAccount() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkBillingAccount();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkBillingAccount() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkBillingAccount();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    @Action(value = "payment/account/transaction/bulk", results = {@Result(type = "json")})
    public String actionBulkBillingAccount() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkBillingAccount();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})


    public String getBankTransaction() throws CommandException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/payment/transaction/get");

        List<Object> txns = (new BankTransactionQueryHandler()).get(q);

        // System.out.println(count);

        setJsonResponseForGet(q, txns);

        return responseStatus;

    }

    public String createBankTransaction() throws Exception {
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateBankTransaction command = new CreateBankTransaction(requestData);
        CommandRegister.getInstance().process(command);
        BankTransaction bta = (BankTransaction) command.getObject();

        return setJsonResponseForCreate(bta, Flags.EntityType.BANK_TRANSACTION);
    }

    public String updateBankTransaction() throws Exception {

        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateBankTransaction command = new UpdateBankTransaction(requestData);
        CommandRegister.getInstance().process(command);
        BankTransaction bta = (BankTransaction) command.getObject();


        return setJsonResponseForUpdate(bta.get_id().toString());
    }

    public void deleteBankTransaction(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteBankTransaction command = new DeleteBankTransaction(map);
            CommandRegister.getInstance().process(command);
            BankTransaction code = (BankTransaction) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    @Action(value = "payment/account/deposit/slip", results = {@Result(type = "json")})
    public String actionBankDepositSlip() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getBankDepositSlip();
        } else if (httpRequest.getMethod().equals("POST")) {
            createDepositSlip();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateDepositSlip();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    private String createDepositSlip() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateDepositSlip command = new CreateDepositSlip(requestData);
        CommandRegister.getInstance().process(command);
        BankDepositSlip bds = (BankDepositSlip) command.getObject();

        setJsonResponseForCreate(bds, Flags.EntityType.DEPOSIT_SLIP);

        return responseStatus;
    }

    private String updateDepositSlip() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateDepositSlip command = new UpdateDepositSlip(requestData);
        CommandRegister.getInstance().process(command);
        BankDepositSlip bds = (BankDepositSlip) command.getObject();

        setJsonResponseForUpdate(bds.get_id().toString());

        return responseStatus;

    }

    private void deleteDepositSlip(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteDepositSlip command = new DeleteDepositSlip(map);
            CommandRegister.getInstance().process(command);
            BankDepositSlip slip = (BankDepositSlip) command.getObject();
            setJsonResponseForDelete(slip);
        } catch (Exception e) {
            setErrorResponse(e);
        }


    }

    public String getBankDepositSlip() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/payment/transaction/deposit_slip/get");

        List<Object> depositSlips = (new BankDepositSlipQueryHandler()).get(q);

        setJsonResponseForGet(q, depositSlips);

        return responseStatus;
    }


    @Action(value = "payment/account/reconciliation", results = {@Result(type = "json")})
    public String actionReconciliation() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getBankReconciliations();
        } else if (httpRequest.getMethod().equals("POST")) {
            processBankReconciliation();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    private String processBankReconciliation() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateBankReconciliation command = new CreateBankReconciliation(requestData);
        CommandRegister.getInstance().process(command);
        BankReconciliation br = (BankReconciliation) command.getObject();

        setJsonResponseForCreate(br, Flags.EntityType.BANK_RECONCILIATION);

        return responseStatus;


    }


    public String getBankReconciliations() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/payment/transaction/get");

        List<Object> recons = (new BankReconciliationQueryHandler()).get(q);

        setJsonResponseForGet(q, recons);

        return responseStatus;
    }

    @Action(value = "payment/account/transaction/clear", results = {@Result(type = "json")})
    public String actionClearBankTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            clearBankTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    private String clearBankTransaction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        ClearBankTransaction command = new ClearBankTransaction(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bankTransactionIds = (List<Object>) command.getObject();


        setStandardJsonResponse(bankTransactionIds, "Bank transactions cleared successfully");

        return responseStatus;
    }

    @Action(value = "payment/account/trust", results = {@Result(type = "json")})
    public String actionTrustTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            //getBankTransactionById();
        } else if (httpRequest.getMethod().equals("POST")) {
            createTrustTransaction();
        } else if (httpRequest.getMethod().equals("PUT")) {
            //updateBankTransaction();
        } else if (httpRequest.getMethod().equals("DELETE")) {
            //deleteBankTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createTrustTransaction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateTrustTransaction command = new CreateTrustTransaction(requestData);
        CommandRegister.getInstance().process(command);
        BankTransaction bta = (BankTransaction) command.getObject();

        setJsonResponseForCreate(bta.get_id().toString(), Flags.EntityType.TRUST_TRANSACTION);

        return responseStatus;
    }

    public String createBulkBankTransaction() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkBankTransaction command = new CreateBulkBankTransaction(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }

    public String updateBulkBankTransaction() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkBankTransaction command = new UpdateBulkBankTransaction(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkBankTransaction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkBankTransaction command = new DeleteBulkBankTransaction(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();

        setJsonResponseForDelete(bta);

        return responseStatus;
    }


    public String createBulkBillingAccount() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkBillingAccount command = new CreateBulkBillingAccount(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }

    public String updateBulkBillingAccount() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkBillingAccount command = new UpdateBulkBillingAccount(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkBillingAccount() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkBillingAccount command = new DeleteBulkBillingAccount(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();

        setJsonResponseForBulkRemove(bta);

        return responseStatus;
    }

    public String actionReceiptById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getReceiptById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteReceipt(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private void deleteReceipt(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeletePaymentReceipt command = new DeletePaymentReceipt(map);
            CommandRegister.getInstance().process(command);
            PaymentReceipt code = (PaymentReceipt) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String getReceiptById(String id) throws CommandException, NotFoundException, JsonProcessingException {


        PaymentReceipt receipt = (PaymentReceipt) (new PaymentReceiptQueryHandler()).getById(id);

        return setJsonResponseForGetById(receipt);
    }

    public String actionAccountById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getAccountById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteBillingAccount(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;

    }

    private String getAccountById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        BillingAccount pro = (BillingAccount) (new BillingAccountQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }


    public String actionBankTransactionById() throws CommandException, NotFoundException, JsonProcessingException {
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getBankTransactionById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteBankTransaction(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private String getBankTransactionById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        BankTransaction pro = (BankTransaction) (new BankTransactionQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }

    public String actionDepositSlipById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getDepositSlipById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteDepositSlip(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private String getDepositSlipById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        BankDepositSlip pro = (BankDepositSlip) (new BankDepositSlipQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }
    public String actionDepositItemById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getDepositItemById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteDepositItem(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }
    private String getDepositItemById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        DepositItem pro = (DepositItem) (new DepositItemQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }
    private void deleteDepositItem(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteDepositItem command = new DeleteDepositItem(map);
            CommandRegister.getInstance().process(command);
            DepositItem item = (DepositItem) command.getObject();
            setJsonResponseForDelete(item);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }
    public String actionReconciliationById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getBankReconciliationById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteDepositItem(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }
    private String getBankReconciliationById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        BankReconciliation pro = (BankReconciliation) (new BankReconciliationQueryHandler()).getById(id);
        return setJsonResponseForGetById(pro);
    }


}*/

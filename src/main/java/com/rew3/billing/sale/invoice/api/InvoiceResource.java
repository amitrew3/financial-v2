/*
package com.rew3.billing.sales.invoice.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.sales.invoice.InvoiceRequestQueryHandler;
import com.rew3.billing.sales.invoice.command.*;
import com.rew3.billing.sales.invoice.model.InvoiceDTO;
import com.rew3.billing.service.PaymentService;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.MethodNotAccepted;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.utils.*;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.wallet.repository.InvoiceQueryHandler;
import com.rew3.billing.sales.invoice.model.Invoice;
import com.rew3.billing.sales.invoice.model.InvoiceRequest;
import com.rew3.common.api.RestAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.InvoiceType;
import com.rew3.common.model.Flags.TimePeriod;
import org.hibernate.Transaction;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class InvoiceResource extends RestAction {

    PaymentService service = new PaymentService();


    private static final long serialVersionUID = 1290L;

//    @Action(value = "invoice", results = {@Result(type = "json")})
//    public String actionInvoice() throws CommandException, NotFoundException, JsonProcessingException, ServletException {
//        String responseStatus = SUCCESS;
//        HttpServletRequest httpRequest = ServletActionContext.getRequest();
//        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
//        InvoiceType invoiceType = InvoiceType.CUSTOMER_INVOICE;
//        if (requestData.containsKey("type")) {
//            invoiceType = (InvoiceType) Flags.InvoiceType.valueOf(requestData.get("type").toString());
//        }
//        if (httpRequest.getMethod().equals("GET")) {
//            responseStatus = getInvoice();
//        } else if (httpRequest.getMethod().equals("POST")) {
//            if (invoiceType.equals(InvoiceType.CUSTOMER_INVOICE)) {
//                responseStatus = createInternalInvoice();
//            } else if (invoiceType.equals(InvoiceType.VENDOR_BILL)) {
//                responseStatus = createExternalInvoice();
//            }
//        } else {
//            setErrorResponse(new NotFoundException("Method not found"));
//        }
//
//        return responseStatus;
//    }

    public String actionMultipleInvoice() throws Exception {
        String response = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        InvoiceType invoiceType = (InvoiceType) Flags.InvoiceType.valueOf(requestData.get("type").toString());


        try {
            if (httpRequest.getMethod().equals("POST")) {

                ArrayList<Object> userIds = (ArrayList<Object>) requestData.get("userIds");

                List<Object> invoices = new ArrayList<>();


                for (Object obj : userIds) {
                    String userId = (String) obj;
                    requestData.put("userId", userId);
                    Invoice invoice = service.createCustomerInvoice(requestData);
                    Invoice dto = (Invoice) new InvoiceQueryHandler().getById(invoice.get_id());
                    invoices.add(dto);
                }

                List<Object> invoiceDTOs = ConvertUtils.convertToDTOs(invoices);

                setJsonResponseForBulkCreation(invoiceDTOs);
                HibernateUtils.commitTransaction(trx);

            } else {
                setErrorResponse(new MethodNotAccepted("Method not acccepted"));
            }
        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        return response;
    }

    public String actionSingleInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getInvoice(requestData);
                    break;

                }
                case "POST": {
                    createInvoice(requestData);
                    break;

                }
                case "PUT": {
                    updateInvoice(requestData);
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

    private void getInvoice(HashMap<String, Object> requestData) {
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> plans = service.getInvoices(requestData);

        Query original = new Query(requestData);

        Long totalCount = new InvoiceQueryHandler().count(original);

        setJsonResponseForGet(requestMap, plans, totalCount, Flags.EntityType.INVOICE);
    }

    private void updateInvoice(HashMap<String, Object> requestData) throws Exception {
        Transaction trx = HibernateUtils.startTransaction();

        try {
            Invoice invoice = service.updateCustomerInvoice(requestData);
            InvoiceDTO dto = service.getInvoiceById(invoice.get_id());
            HibernateUtils.commitTransaction(trx);

            setJsonResponseForUpdate(dto);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }

    }

    private void createInvoice(HashMap<String, Object> requestData) throws Exception {

        Transaction trx = HibernateUtils.startTransaction();
        Invoice invoice = null;

        try {
            invoice = service.createCustomerInvoice(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
        InvoiceDTO dto = service.getInvoiceById(invoice.get_id());


        setJsonResponseForCreate(dto, Flags.EntityType.INVOICE);


    }

    public String createInternalInvoice() throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateCustomerInvoice command = new CreateCustomerInvoice(requestData);
            CommandRegister.getInstance().process(command);
            Invoice invoice = (Invoice) command.getObject();
            invoice = (Invoice) new InvoiceQueryHandler().getById(invoice.get_id());
            setJsonResponseForCreate(invoice, Flags.EntityType.INVOICE);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }


    public String createExternalInvoice() throws CommandException, NotFoundException, JsonProcessingException, ServletException {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateVendorBill command = new CreateVendorBill(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> invoices = (List<Object>) command.getObject();
            setJsonResponseForBulkCreation(invoices);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }


    @Action(value = "invoice/{id}/accept", results = {@Result(type = "json")})
    public String actionAcceptInternalInvoice() throws Exception {
        String responseStatus = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();

        if (httpRequest.getMethod().equals("POST")) {
            responseStatus = acceptInternalInvoice();
        } else {
            responseStatus = "HttpMethodNotAccepted";
        }

        return responseStatus;
    }

    public String acceptInternalInvoice() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        AcceptInvoice command = new AcceptInvoice(requestData);
        CommandRegister.getInstance().process(command);
        InvoiceRequest ir = (InvoiceRequest) command.getObject();

        setJsonResponseForCreate(ir.toString(), Flags.EntityType.INVOICE_REQUEST);
        return responseStatus;
    }

    public String getInvoice() throws CommandException, NotFoundException {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        if (requestData.containsKey("customerId")) {
            requestData.put("customerId", Parser.csvToList(requestData.get("customerId")));
        }
        if (requestData.containsKey("userId")) {
            requestData.put("userId", Parser.csvToList(requestData.get("userId")));
        }
        if (requestData.containsKey("dueStatus")) {
            requestData.put("dueStatus", Parser.csvToList(requestData.get("dueStatus")));
        }
        if (requestData.containsKey("paymentStatus")) {
            requestData.put("paymentStatus", Parser.csvToList(requestData.get("paymentStatus")));
        }
        if (requestData.containsKey("customerStatus")) {
            requestData.put("customerStatus", Parser.csvToList(requestData.get("customerStatus")));
        }
        if (requestData.containsKey("userStatus")) {
            requestData.put("userStatus", Parser.csvToList(requestData.get("userStatus")));
        }
        if (requestData.containsKey("refundStatus")) {
            requestData.put("refundStatus", Parser.csvToList(requestData.get("refundStatus")));
        }
        if (requestData.containsKey("writeoffStatus")) {
            requestData.put("writeoffStatus", Parser.csvToList(requestData.get("writeoffStatus")));
        }

        Query q = new Query(requestData, "billing/invoice/get");

        List<Object> invoices = (new InvoiceQueryHandler()).get(q);

        setJsonResponseForGet(q, invoices);

        return responseStatus;
    }

    @Action(value = "invoice/refund", results = {@Result(type = "json")})
    public String actionRefundRequest() throws Exception {
        String responseStatus = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();

        if (httpRequest.getMethod().equals("GET")) {
            responseStatus = getRefundRequest();
        } else if (httpRequest.getMethod().equals("POST")) {
            responseStatus = createRefundRequest();
        } else {
            responseStatus = "HttpMethodNotAccepted";
        }

        return responseStatus;
    }

    public String createRefundRequest() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateInvoiceRefundRequest command = new CreateInvoiceRefundRequest(requestData);
        CommandRegister.getInstance().process(command);
        InvoiceRequest ir = (InvoiceRequest) command.getObject();

      */
/*  if (ir != null) {
            responseStatus = SUCCESS;
            setJsonResponse("Invoice refund has been successfully created.");
            getData().put("invoiceRequest", ir.get_id());
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    public String getRefundRequest() {
        String responseStatus = SUCCESS;
        //setJsonResponse();

        return responseStatus;
    }

    @Action(value = "invoice/refund/{id}/response", results = {@Result(type = "json")})
    public String actionRefundResponse() throws Exception {
        String responseStatus = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();

        if (httpRequest.getMethod().equals("POST")) {
            responseStatus = refundResponse();
        } else {
            responseStatus = "HttpMethodNotAccepted";
        }

        return responseStatus;
    }

    public String refundResponse() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateInvoiceRefundResponse command = new CreateInvoiceRefundResponse(requestData);
        CommandRegister.getInstance().process(command);
        InvoiceRequest ir = (InvoiceRequest) command.getObject();

       */
/* if (ir != null) {
            responseStatus = SUCCESS;
            setJsonResponse("Invoice refund has been successfully responded.");
            getData().put("invoiceRequest", ir.get_id());
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    @Action(value = "invoice/{id}/writeoff", results = {@Result(type = "json")})
    public String actionWriteoff() throws Exception {
        String responseStatus = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();

        if (httpRequest.getMethod().equals("POST")) {
            responseStatus = writeoff();
        } else {
            responseStatus = "HttpMethodNotAccepted";
        }

        return responseStatus;
    }

    public String writeoff() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateInvoiceWriteoff command = new CreateInvoiceWriteoff(requestData);
        CommandRegister.getInstance().process(command);
        InvoiceRequest ir = (InvoiceRequest) command.getObject();

       */
/* if (ir != null) {
            responseStatus = SUCCESS;
            setJsonResponse("Invoice has been successfully written off.");
            getData().put("invoiceRequest", ir.get_id());
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    @Action(value = "invoice/payable", results = {@Result(type = "json")})
    public String getPayableInvoice() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "invoice/get");

        q.set("customerId", getRequest().get("customerId"));
        q.set("paymentStatus", "unpaid,partial_paid");
        Timestamp invoiceDate = DateTime.getNextTimestamp(DateTime.getMidnightTimestamp(DateTime.getCurrentTimestamp()),
                TimePeriod.DAILY);
        q.set("invoiceDateBefore", invoiceDate.toString());

        List<Object> invoices = (new InvoiceQueryHandler()).get(q);

       */
/* if (q.validate()) {
            responseStatus = SUCCESS;
            getData().put("invoices", invoices);
            getData().put("logs", APILogger.getList());
            setJsonResponse();
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("logs", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    @Action(value = "invoice/receivable", results = {@Result(type = "json")})
    public String getReceivableInvoice() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "invoice/get");

        q.set("userId", getRequest().get("userId"));
        q.set("paymentStatus", "unpaid,partial_paid");
        Timestamp invoiceDate = DateTime.getNextTimestamp(DateTime.getMidnightTimestamp(DateTime.getCurrentTimestamp()),
                TimePeriod.DAILY);
        q.set("invoiceDateBefore", invoiceDate.toString());

        List<Object> invoices = (new InvoiceQueryHandler()).get(q);

       */
/* if (q.validate()) {
            responseStatus = SUCCESS;
            getData().put("invoices", invoices);
            getData().put("logs", APILogger.getList());
            setJsonResponse();
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("logs", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    public String actionInvoiceById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getInvoiceById(id);
                    break;

                }
                case "DELETE": {
                    deleteInvoice(id);
                    break;
                }
                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }


        return SUCCESS;
    }


    public String actionBulkInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    createBulkInvoice();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }

        return response;
    }

    public String actionUpdateBulkInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();


        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    updateBulkInvoice();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return response;

    }


    public String actionRemoveBulkInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    removeBulkInvoice();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }


        return response;
    }

    private void deleteInvoice(String id) {
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        try {
            DeleteInvoice command = new DeleteInvoice(map);
            CommandRegister.getInstance().process(command);
            Invoice invoice = (Invoice) command.getObject();
            InvoiceDTO dto = ConvertUtils.convertToInvoiceDTO(invoice);

            setJsonResponseForDelete(dto);
            HibernateUtils.commitTransaction(trx);
        } catch (Exception e) {
            setErrorResponse(e);
            HibernateUtils.rollbackTransaction(trx);

        } finally {
            HibernateUtils.closeSession();
        }
    }

    private String getInvoiceById(String id) throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        Invoice invoice = (Invoice) (new InvoiceQueryHandler()).getById(id);

        InvoiceDTO dto = ConvertUtils.convertToInvoiceDTO(invoice);
        setJsonResponseForGetById(dto);
        return responseStatus;

    }

    public String actionInvoiceRequestById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getInvoiceRequestById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteInvoiceRequest(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private void deleteInvoiceRequest(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteInvoiceRequest command = new DeleteInvoiceRequest(map);
            CommandRegister.getInstance().process(command);
            InvoiceRequest ivr = (InvoiceRequest) command.getObject();
            setJsonResponseForDelete(ivr);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    private String getInvoiceRequestById(String id) throws CommandException, NotFoundException {


        InvoiceRequest pro = (InvoiceRequest) (new InvoiceRequestQueryHandler()).getById(id);

        return setJsonResponseForGetById(pro);

    }


    public String createBulkInvoice() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkInvoice command = new CreateBulkInvoice(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> invoices = (List<Object>) command.getListObject();
            List<Object> invoiceDtos = ConvertUtils.convertToDTOs(invoices);
            setJsonResponseForBulkCreation(invoiceDtos);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String updateBulkInvoice() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkInvoice command = new CreateBulkInvoice(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> invoices = (List<Object>) command.getListObject();

            List<Object> dtos = ConvertUtils.convertToDTOs(invoices);
            setJsonResponseForBulkUpdate(dtos);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String removeBulkInvoice() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkInvoice command = new DeleteBulkInvoice(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            List<Object> dtos = ConvertUtils.convertToDTOs(bta);
            setJsonResponseForBulkRemove(dtos);
            HibernateUtils.commitTransaction(trx);
        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


}
*/

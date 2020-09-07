package com.rew3.billing.sale.estimate.api;/*
package com.rew3.billing.sales.invoice.api;

import com.rew3.billing.sales.invoice.RecurringInvoiceQueryHandler;
import com.rew3.billing.sales.invoice.command.CreateBulkRecurringInvoice;
import com.rew3.billing.sales.invoice.command.DeleteBulkRecurringInvoice;
import com.rew3.billing.sales.invoice.command.DeleteRecurringInvoice;
import com.rew3.billing.sales.invoice.model.RecurringInvoice;
import com.rew3.billing.sales.invoice.model.RecurringInvoiceDTO;
import com.rew3.billing.service.PaymentService;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.ConvertUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
public class RecurringInvoiceResource extends RestAction {
    PaymentService service = new PaymentService();

    public String actionRecurringInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getRecurringInvoice(requestData);
                    break;

                }
                case "POST": {
                    createRecurringInvoice(requestData);
                    break;

                }
                case "PUT": {
                    updateRecurringInvoice(requestData);
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

    private void getRecurringInvoice(HashMap<String, Object> requestData) {
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> recurringInvoices = service.getRecurringInvoices(requestData);

        Query original = new Query(requestData);

        Long totalCount = new RecurringInvoiceQueryHandler().count(original);

        setJsonResponseForGet(requestMap, recurringInvoices, totalCount, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);
    }

    private void createRecurringInvoice(HashMap<String, Object> requestData) throws NotFoundException, CommandException {
        Transaction trx = HibernateUtils.startTransaction();
        RecurringInvoice recurringInvoice = null;

        try {
            recurringInvoice = service.createRecurringInvoice(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        RecurringInvoiceDTO dto = service.getRecurringInvoiceById(recurringInvoice.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);


    }

    private void updateRecurringInvoice(HashMap<String, Object> requestData) throws Exception {

        Transaction trx = HibernateUtils.startTransaction();
        RecurringInvoice recurringInvoice = null;

        try {
            recurringInvoice = service.updateRecurringInvoice(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            throw e;
//            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
        RecurringInvoiceDTO dto = service.getRecurringInvoiceById(recurringInvoice.get_id());
        setJsonResponseForUpdate(dto);

    }


    public String actionRecurringInvoiceById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getRecurringInvoiceById(id);
                    break;

                }
                case "DELETE": {
                    deleteRecurringInvoice(id);
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

    private void deleteRecurringInvoice(String id) {
        Transaction trx = HibernateUtils.startTransaction();
        try {
            HashMap map = new HashMap();
            map.put("id", id);
            DeleteRecurringInvoice deleteRecurringInvoiceCommand = new DeleteRecurringInvoice(map);
            CommandRegister.getInstance().process(deleteRecurringInvoiceCommand);
            RecurringInvoice recurringInvoice = (RecurringInvoice) deleteRecurringInvoiceCommand.getObject();
            HibernateUtils.commitTransaction(trx);

            RecurringInvoiceDTO dto = ConvertUtils.convertToRecurringInvoiceDTO(recurringInvoice);
            setJsonResponseForDelete(dto);
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private void getRecurringInvoiceById(String id) throws NotFoundException, CommandException {
        RecurringInvoiceDTO recurringInvoice = service.getRecurringInvoiceById(id);
        setJsonResponseForGetById(recurringInvoice);
    }

    public String actionBulkRecurringInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    createBulkRecurringInvoice();
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

    public String actionUpdateBulkRecurringInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();


        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    updateBulkRecurringInvoice();
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


    public String actionRemoveBulkRecurringInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    removeBulkRecurringInvoice();
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

    public String createBulkRecurringInvoice() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<Object> recurringInvoices = null;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkRecurringInvoice command = new CreateBulkRecurringInvoice(requestData);
            CommandRegister.getInstance().process(command);
            recurringInvoices = (List<Object>) command.getListObject();
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }


        List<Object> recurringInvoiceDtos = ConvertUtils.convertToDTOs(recurringInvoices);
        setJsonResponseForBulkCreation(recurringInvoiceDtos);


        return responseStatus;
    }


    public String updateBulkRecurringInvoice() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkRecurringInvoice command = new CreateBulkRecurringInvoice(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> recurringInvoices = (List<Object>) command.getListObject();

            List<Object> dtos = ConvertUtils.convertToDTOs(recurringInvoices);
            setJsonResponseForBulkUpdate(dtos);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String removeBulkRecurringInvoice() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkRecurringInvoice command = new DeleteBulkRecurringInvoice(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            List<Object> dtos = ConvertUtils.convertToDTOs(bta);
            setJsonResponseForBulkRemove(dtos);
            HibernateUtils.commitTransaction(trx);
        } catch (Exception ex) {
            setErrorResponse(ex);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


}




*/

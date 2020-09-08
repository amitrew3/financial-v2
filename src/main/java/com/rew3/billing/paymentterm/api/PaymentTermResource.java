package com.rew3.billing.paymentterm.api;/*
package com.rew3.billing.sales.invoice.api;

import com.rew3.billing.sales.invoice.PaymentTermQueryHandler;
import com.rew3.billing.sales.invoice.command.CreateBulkTerm;
import com.rew3.billing.sales.invoice.command.DeleteBulkTerm;
import com.rew3.billing.sales.invoice.command.DeleteTerm;
import com.rew3.billing.sales.invoice.model.PaymentTerm;
import com.rew3.billing.sales.invoice.model.PaymentTermDTO;
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
public class PaymentTermResource extends RestAction {
    PaymentService service = new PaymentService();

    public String actionPaymentTerm() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getPaymentTerm(requestData);
                    break;

                }
                case "POST": {
                    createPaymentTerm(requestData);
                    break;

                }
                case "PUT": {
                    updatePaymentTerm(requestData);
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

    private void getPaymentTerm(HashMap<String, Object> requestData) {
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> plans = service.getPaymentTerms(requestData);

        Query original = new Query(requestData);

        Long totalCount = new PaymentTermQueryHandler().count(original);

        setJsonResponseForGet(requestMap, plans, totalCount, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);
    }

    private void createPaymentTerm(HashMap<String, Object> requestData) throws NotFoundException, CommandException {
        Transaction trx = HibernateUtils.startTransaction();
        PaymentTerm plan = null;

        try {
            plan = service.createUpdatePaymentTerm(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        PaymentTermDTO dto = service.getPaymentTermById(plan.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);


    }

    private void updatePaymentTerm(HashMap<String, Object> requestData) throws Exception {

        Transaction trx = HibernateUtils.startTransaction();
        PaymentTerm plan = null;

        try {
            plan = service.createUpdatePaymentTerm(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            throw e;
//            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
        PaymentTermDTO dto = service.getPaymentTermById(plan.get_id());
        setJsonResponseForUpdate(dto);

    }


    public String actionPaymentTermById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getPaymentTermById(id);
                    break;

                }
                case "DELETE": {
                    deletePaymentTerm(id);
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

    private void deletePaymentTerm(String id) {
        Transaction trx = HibernateUtils.startTransaction();
        try {
            HashMap map = new HashMap();
            map.put("id", id);
            DeleteTerm deletePaymentTermCommand = new DeleteTerm(map);
            CommandRegister.getInstance().process(deletePaymentTermCommand);
            PaymentTerm plan = (PaymentTerm) deletePaymentTermCommand.getObject();
            HibernateUtils.commitTransaction(trx);

            PaymentTermDTO dto = ConvertUtils.convertToPaymentTermDTO(plan);
            setJsonResponseForDelete(dto);
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private void getPaymentTermById(String id) throws NotFoundException, CommandException {
        PaymentTermDTO plan = service.getPaymentTermById(id);
        setJsonResponseForGetById(plan);
    }

    public String actionBulkPaymentTerm() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    createBulkPaymentTerm();
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

    public String actionUpdateBulkPaymentTerm() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();


        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    updateBulkPaymentTerm();
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


    public String actionRemoveBulkPaymentTerm() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    removeBulkPaymentTerm();
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

    public String createBulkPaymentTerm() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<Object> plans = null;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkTerm command = new CreateBulkTerm(requestData);
            CommandRegister.getInstance().process(command);
            plans = (List<Object>) command.getListObject();
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }


        List<Object> planDtos = ConvertUtils.convertToDTOs(plans);
        setJsonResponseForBulkCreation(planDtos);


        return responseStatus;
    }


    public String updateBulkPaymentTerm() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkTerm command = new CreateBulkTerm(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> plans = (List<Object>) command.getListObject();

            List<Object> dtos = ConvertUtils.convertToDTOs(plans);
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


    public String removeBulkPaymentTerm() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkTerm command = new DeleteBulkTerm(requestData);
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

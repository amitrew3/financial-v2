/*
package com.rew3.billing.expense.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.expense.ExpenseQueryHandler;
import com.rew3.billing.expense.command.*;
import com.rew3.billing.expense.model.Expense;
import com.rew3.billing.expense.model.ExpenseDTO;
import com.wallet.repository.InvoiceQueryHandler;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceDTO;
import com.rew3.billing.service.PaymentService;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.ConvertUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class ExpenseResource extends RestAction {

    private static final long serialVersionUID = 1290L;
    PaymentService service = new PaymentService();

    @Action(value = "expense", results = {@Result(type = "json")})
    public String actionExpense() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            if (httpRequest.getMethod().equals("POST")) {

                createExpense(httpRequest, requestData);

            } else if (httpRequest.getMethod().equals("PUT")) {
                updateExpense(httpRequest, requestData);


            } else if (httpRequest.getMethod().equals("GET")) {
                getExpense();

            } else {
                setErrorResponse(new NotFoundException("Method not found"));
            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }

        return response;
    }

    public String createExpense(HttpServletRequest httpRequest, HashMap<String, Object> requestData) throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        Expense expense = null;

        try {
            expense = service.createUpdateExpense(requestData, httpRequest.getMethod());
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
        ExpenseDTO dto = service.getExpenseById(expense.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.INVOICE);
        return responseStatus;
    }

    public String updateExpense(HttpServletRequest httpRequest, HashMap<String, Object> requestData) throws Exception {

        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        Expense expense = null;

        try {
            expense = service.createUpdateExpense(requestData, httpRequest.getMethod());
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
        ExpenseDTO dto = service.getExpenseById(expense.get_id());
        setJsonResponseForUpdate(dto);
        return responseStatus;
    }

    public String getExpense() throws IllegalAccessException, CommandException, ParseException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> expensesList = new ExpenseQueryHandler().get(q);

        Query original = new Query(requestData);

        Long totalCount = new ExpenseQueryHandler().count(original);


        setJsonResponseForGet(requestMap, expensesList, totalCount, Flags.EntityType.EXPENSE);
        return responseStatus;
    }


    public String actionExpenseById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getExpenseById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteExpense(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }
        return response;
    }


    private void getExpenseById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        ExpenseDTO dto = service.getExpenseById(id);

        setJsonResponseForGetById(dto);

    }

    public void deleteExpense(String id) throws CommandException, NotFoundException {
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        try {
            DeleteExpense command = new DeleteExpense(map);
            CommandRegister.getInstance().process(command);
            Expense invoice = (Expense) command.getObject();
            ExpenseDTO dto = ConvertUtils.convertToExpenseDTO(invoice);

            setJsonResponseForDelete(dto);
            HibernateUtils.commitTransaction(trx);
        } catch (Exception e) {
            setErrorResponse(e);
            HibernateUtils.rollbackTransaction(trx);

        } finally {
            HibernateUtils.closeSession();
        }

    }


    public String actionBulkExpense() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkExpense();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/expense/bulk/remove", results = {@Result(type = "json")})

    public String actionUpdateBulkExpense() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkExpense();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String actionRemoveBulkExpense() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {

            removeBulkExpense();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createBulkExpense() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkExpense command = new CreateBulkExpense(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            setJsonResponseForBulkCreation(bta);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }


    public String updateBulkExpense() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {
            UpdateBulkExpense command = new UpdateBulkExpense(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();

            setJsonResponseForBulkUpdate(bta);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }


    public String removeBulkExpense() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkExpense command = new DeleteBulkExpense(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            setJsonResponseForBulkRemove(bta);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }


}
*/

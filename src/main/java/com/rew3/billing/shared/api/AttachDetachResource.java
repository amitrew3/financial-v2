/*
package com.rew3.billing.shared.api;

import com.rew3.billing.expense.ExpenseQueryHandler;
import com.rew3.billing.expense.model.Expense;
import com.rew3.billing.expense.model.ExpenseReference;
import com.wallet.repository.InvoiceQueryHandler;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceReference;
import com.rew3.billing.shared.command.*;
import com.rew3.commission.commissionplan.CommissionPlanQueryHandler;
import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.commission.commissionplan.model.CommissionPlanReference;
import com.rew3.commission.transaction.TransactionQueryHandler;
import com.rew3.commission.transaction.model.RmsTransaction;
import com.rew3.commission.transaction.model.TransactionReference;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1/billing")
public class AttachDetachResource extends RestAction {

    @Action(value = "attach", results = {@Result(type = "json")})
    public String actionAttachTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    attachTheTransaction();
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

    @Action(value = "detach", results = {@Result(type = "json")})
    public String actionDetatchTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    detatchTheTransaction();
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

    @Action(value = "attach", results = {@Result(type = "json")})
    public String actionAttachCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    attachTheCommisionPlan();
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

    @Action(value = "detach", results = {@Result(type = "json")})
    public String actionDetatchCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    detachTheCommissionPlan();
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
    public String actionAttachInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    attachTheInvoice();
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
    public String actionDetatchInvoice() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    detachTheInvoice();
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
    public String actionAttachExpense() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    attachTheExpense();
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
    public String actionDetatchExpense() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    detachTheExpense();
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



    private String attachTheTransaction() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            AttachToTransaction command = new AttachToTransaction(requestData);
            CommandRegister.getInstance().process(command);
            TransactionReference bta = (TransactionReference) command.getObject();

            RmsTransaction transaction = (RmsTransaction) new TransactionQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(transaction);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    private String detatchTheTransaction() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            DetachFromTransaction command = new DetachFromTransaction(requestData);
            CommandRegister.getInstance().process(command);
            boolean success = (boolean) command.getObject();
            RmsTransaction transaction = (RmsTransaction) new TransactionQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(transaction);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    private String attachTheCommisionPlan() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            AttachToCommissionPlan command = new AttachToCommissionPlan(requestData);
            CommandRegister.getInstance().process(command);
            CommissionPlanReference bta = (CommissionPlanReference) command.getObject();

            CommissionPlan commissionPlan = (CommissionPlan) new CommissionPlanQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(commissionPlan);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    private String detachTheCommissionPlan() {


        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            DetachFromCommissionPlan command = new DetachFromCommissionPlan(requestData);
            CommandRegister.getInstance().process(command);
            boolean success = (boolean) command.getObject();
            CommissionPlan commissionPlan = (CommissionPlan) new CommissionPlanQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(commissionPlan);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    private String globalAttach() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        String id = requestData.get("id").toString();
        String entity = requestData.get("entity").toString();
        try {
            AttachToTransaction command = new AttachToTransaction(requestData);
            CommandRegister.getInstance().process(command);
            TransactionReference bta = (TransactionReference) command.getObject();
            setJsonResponseForCreate(bta, Flags.EntityType.RMS_TRANSACTION);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }
    private String attachTheInvoice() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            AttachToInvoice command = new AttachToInvoice(requestData);
            CommandRegister.getInstance().process(command);
            InvoiceReference bta = (InvoiceReference) command.getObject();

            Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(invoice);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    private String detachTheInvoice() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            DetachFromInvoice command = new DetachFromInvoice(requestData);
            CommandRegister.getInstance().process(command);
            boolean success = (boolean) command.getObject();
            Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(invoice);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }
    private String attachTheExpense() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            AttachToExpense command = new AttachToExpense(requestData);
            CommandRegister.getInstance().process(command);
            ExpenseReference bta = (ExpenseReference) command.getObject();

            Expense expense = (Expense) new ExpenseQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(expense);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    private String detachTheExpense() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            DetachFromExpense command = new DetachFromExpense(requestData);
            CommandRegister.getInstance().process(command);
            boolean success = (boolean) command.getObject();
            Expense expense = (Expense) new ExpenseQueryHandler().getById(requestData.get("id").toString());
            setJsonResponseForGetById(expense);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }


}
*/

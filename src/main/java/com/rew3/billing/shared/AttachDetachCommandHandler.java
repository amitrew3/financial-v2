package com.rew3.billing.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.expense.ExpenseQueryHandler;
import com.rew3.billing.expense.model.Expense;
import com.rew3.billing.expense.model.ExpenseReference;
import com.rew3.billing.invoice.InvoiceQueryHandler;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceReference;
import com.rew3.billing.shared.command.*;
import com.rew3.commission.commissionplan.CommissionPlanQueryHandler;
import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.commission.commissionplan.model.CommissionPlanReference;
import com.rew3.commission.transaction.TransactionQueryHandler;
import com.rew3.commission.transaction.model.RmsTransaction;
import com.rew3.commission.transaction.model.TransactionReference;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.utils.Rew3StringBuiler;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class AttachDetachCommandHandler implements ICommandHandler {

    public static void registerCommands() {

        CommandRegister.getInstance().registerHandler(AttachToTransaction.class, AttachDetachCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DetachFromTransaction.class, AttachDetachCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AttachToCommissionPlan.class, AttachDetachCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DetachFromCommissionPlan.class, AttachDetachCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AttachToInvoice.class, AttachDetachCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DetachFromInvoice.class, AttachDetachCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AttachToExpense.class, AttachDetachCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DetachFromExpense.class, AttachDetachCommandHandler.class);


    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof AttachToTransaction) {
            handle((AttachToTransaction) c);
        } else if (c instanceof DetachFromTransaction) {
            handle((DetachFromTransaction) c);
        } else if (c instanceof AttachToCommissionPlan) {
            handle((AttachToCommissionPlan) c);
        } else if (c instanceof DetachFromCommissionPlan) {
            handle((DetachFromCommissionPlan) c);
        } else if (c instanceof AttachToInvoice) {
            handle((AttachToInvoice) c);
        } else if (c instanceof DetachFromInvoice) {
            handle((DetachFromInvoice) c);
        } else if (c instanceof DetachFromInvoice) {
            handle((DetachFromInvoice) c);
        } else if (c instanceof AttachToExpense) {
            handle((DetachFromExpense) c);
        }

    }

    public void handle(AttachToTransaction c) throws Exception {
        Transaction trx = c.getTransaction();
        String entityId = c.get("entityId").toString();

        String entity = c.get("type").toString();


        try {
            TransactionReference di = this._handleSaveTransactionReference(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(di);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }


    public void handle(DetachFromTransaction c) throws Exception {
        Transaction trx = c.getTransaction();

        try {
            boolean success = this._handleDetachTransactionReference(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(success);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    private TransactionReference _handleSaveTransactionReference(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        TransactionReference transactionReference = new TransactionReference();

        if (c.has("id")) {
            RmsTransaction transaction = (RmsTransaction) (new TransactionQueryHandler().getById(c.get("id").toString()));
            transactionReference.setTransaction(transaction);
        }


        if (c.get("entityId") != null) {
            transactionReference.setEntityId(c.get("entityId").toString());

        }
        if (c.get("entity") != null) {
            transactionReference.setEntity(c.get("entity").toString());
        }
        if (c.get("module") != null) {
            transactionReference.setModule(c.get("module").toString());
        }
        if (c.get("type") != null) {
            transactionReference.setType(c.get("type").toString());
        }
        transactionReference = (TransactionReference) HibernateUtils.defaultSave(transactionReference, c.getTransaction());

        return transactionReference;

    }


    private boolean _handleDetachTransactionReference(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {
        Transaction trx = c.getTransaction();
        boolean success = false;
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM TransactionReference WHERE transaction_id = :id AND entity_id=:entityId AND entity=:entity AND module=:module AND type=:type";

        sqlParams.put("id", c.get("id").toString());
        sqlParams.put("entityId", c.get("entityId").toString());
        sqlParams.put("entity", c.get("entity").toString());
        sqlParams.put("module", c.get("module").toString());
        sqlParams.put("type", c.get("type").toString());

        success = HibernateUtils.query(sql, sqlParams, trx);

        c.setObject(success);
        return success;

    }


    public void handle(AttachToCommissionPlan c) throws Exception {
        Transaction trx = HibernateUtils.startTransaction();

        try {
            CommissionPlanReference di = this._handleSaveCommissionPlanReference(c);
            c.setObject(di);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }


    }


    public void handle(DetachFromCommissionPlan c) throws Exception {
        boolean success = false;

        Transaction trx = HibernateUtils.startTransaction();

        try {
            success = this._handleDetachCommissionPlanReference(c);
            c.setObject(success);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }


    }

    private CommissionPlanReference _handleSaveCommissionPlanReference(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        CommissionPlanReference commissionPlanReference = new CommissionPlanReference();

        if (c.has("id")) {
            CommissionPlan transaction = (CommissionPlan) (new CommissionPlanQueryHandler().getById(c.get("id").toString()));
            commissionPlanReference.setCommissionPlan(transaction);
        }


        if (c.get("entityId") != null) {
            commissionPlanReference.setEntityId(c.get("entityId").toString());

        }

        if (c.get("title") != null) {
            commissionPlanReference.setTitle(c.get("title").toString());

        }
        if (c.get("entity") != null) {
            commissionPlanReference.setEntity(c.get("entity").toString());
        }
        if (c.get("module") != null) {
            commissionPlanReference.setModule(c.get("module").toString());
        }

        if (c.has("data")) {
            HashMap<String, Object> contact = (HashMap<String, Object>) c.get("data");
            if (contact.get("type") != null) {
                commissionPlanReference.setType(contact.get("type").toString());
            }
        }


        commissionPlanReference = (CommissionPlanReference) HibernateUtils.defaultSave(commissionPlanReference);

        return commissionPlanReference;

    }


    private boolean _handleDetachCommissionPlanReference(ICommand c) {
        boolean success = false;
        HashMap<String, Object> sqlParams = new HashMap<>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");
        if (c.has("id")) {
            builder.append("AND");
            builder.append("commission_plan_id");
            builder.append("= :id ");
            sqlParams.put("id", c.get("id").toString());
        }
        if (c.has("entityId")) {
            builder.append("AND");
            builder.append("entity_id");
            builder.append("= :entityId ");
            sqlParams.put("entityId", c.get("entityId").toString());
        }

        if (c.has("entity")) {
            builder.append("AND");
            builder.append("entity");
            builder.append("= :entity ");
            sqlParams.put("entity", c.get("entity").toString());
        }
        if (c.has("module")) {
            builder.append("AND");
            builder.append("module");
            builder.append("= :module ");
            sqlParams.put("module", c.get("module").toString());
        }
        if (c.has("data")) {
            Map map = (Map) c.get("data");
            String type = map.get("type").toString();

            builder.append("AND");
            builder.append("type");
            builder.append("= :type ");
            sqlParams.put("type", type);
        }

        if (c.has("title")) {
            builder.append("AND");
            builder.append("title");
            builder.append("= :title ");
            sqlParams.put("title", c.get("title").toString());
        }


        success = HibernateUtils.query("delete From CommissionPlanReference "+builder.getValue(), sqlParams);
        return success;

    }

    public void handle(AttachToInvoice c) throws Exception {
        Transaction trx = HibernateUtils.startTransaction();

        try {
            InvoiceReference di = this._handleSaveInvoiceReference(c);
            c.setObject(di);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }


    }


    public void handle(DetachFromInvoice c) throws Exception {
        boolean success = false;

        Transaction trx = HibernateUtils.startTransaction();

        try {
            success = this._handleDetachInvoiceReference(c);
            c.setObject(success);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }


    }

    private InvoiceReference _handleSaveInvoiceReference(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        InvoiceReference invoiceReference = new InvoiceReference();

        if (c.has("id")) {
            Invoice invoice = (Invoice) (new InvoiceQueryHandler().getById(c.get("id").toString()));
            invoiceReference.setInvoice(invoice);
        }


        if (c.get("entityId") != null) {
            invoiceReference.setEntityId(c.get("entityId").toString());

        }
        if (c.get("entity") != null) {
            invoiceReference.setEntity(c.get("entity").toString());
        }
        if (c.get("module") != null) {
            invoiceReference.setModule(c.get("module").toString());
        }
        if (c.get("type") != null) {
            invoiceReference.setType(c.get("type").toString());
        }
        invoiceReference = (InvoiceReference) HibernateUtils.defaultSave(invoiceReference);

        return invoiceReference;

    }


    private boolean _handleDetachInvoiceReference(ICommand c) {
        boolean success = false;
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM InvoiceReference WHERE invoice_id = :id AND entity_id=:entityId AND entity=:entity AND module=:module AND type=:type";

        sqlParams.put("id", c.get("id").toString());
        sqlParams.put("entityId", c.get("entityId").toString());
        sqlParams.put("entity", c.get("entity").toString());
        sqlParams.put("module", c.get("module").toString());
        sqlParams.put("type", c.get("type").toString());

        success = HibernateUtils.query(sql, sqlParams);
        return success;

    }


    public void handle(AttachToExpense c) throws Exception {
        Transaction trx = HibernateUtils.startTransaction();

        try {
            ExpenseReference di = this._handleSaveExpenseReference(c);
            c.setObject(di);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }


    }


    public void handle(DetachFromExpense c) throws Exception {
        boolean success = false;

        Transaction trx = HibernateUtils.startTransaction();

        try {
            success = this._handleDetachExpenseReference(c);
            c.setObject(success);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }


    }

    private ExpenseReference _handleSaveExpenseReference(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        ExpenseReference expenseReference = new ExpenseReference();

        if (c.has("id")) {
            Expense expense = (Expense) (new ExpenseQueryHandler().getById(c.get("id").toString()));
            expenseReference.setExpense(expense);
        }


        if (c.get("entityId") != null) {
            expenseReference.setEntityId(c.get("entityId").toString());

        }
        if (c.get("entity") != null) {
            expenseReference.setEntity(c.get("entity").toString());
        }
        if (c.get("module") != null) {
            expenseReference.setModule(c.get("module").toString());
        }
        if (c.get("type") != null) {
            expenseReference.setType(c.get("type").toString());
        }
        expenseReference = (ExpenseReference) HibernateUtils.defaultSave(expenseReference);

        return expenseReference;

    }


    private boolean _handleDetachExpenseReference(ICommand c) {
        boolean success = false;
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM ExpenseReference WHERE expense_id = :id AND entity_id=:entityId AND entity=:entity AND module=:module AND type=:type";

        sqlParams.put("id", c.get("id").toString());
        sqlParams.put("entityId", c.get("entityId").toString());
        sqlParams.put("entity", c.get("entity").toString());
        sqlParams.put("module", c.get("module").toString());
        sqlParams.put("type", c.get("type").toString());

        success = HibernateUtils.query(sql, sqlParams);
        return success;

    }


}

package com.rew3.billing.service;

import com.rew3.purchase.expense.command.CreateExpense;
import com.rew3.purchase.expense.model.Expense;
import com.rew3.purchase.expense.model.ExpenseDTO;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.paymentterm.PaymentTermQueryHandler;
import com.rew3.sale.invoice.RecurringInvoiceQueryHandler;
import com.rew3.sale.invoice.command.*;
import com.rew3.sale.invoice.model.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.Converters;
import com.rew3.sale.recurringinvoice.command.CreateRecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoiceDTO;

import java.util.HashMap;
import java.util.List;

public class PaymentService {
    public Invoice createCustomerInvoice(HashMap<String, Object> requestData) throws Exception {


        CreateInvoice command = new CreateInvoice(requestData);
        CommandRegister.getInstance().process(command);
        Invoice invoice = (Invoice) command.getObject();
        return invoice;

    }


    public InvoiceDTO getInvoiceById(String id) throws NotFoundException, CommandException {
        Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(id);
        return Converters.convertToInvoiceDTO(invoice);

    }


    public List<Object> getInvoices(HashMap<String, Object> requestData) {
        List<Object> lists = new InvoiceQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }

    public Expense createUpdateExpense(HashMap<String, Object> requestData, String method) throws Exception {
        Expense expense = null;
        CreateExpense command = new CreateExpense(requestData, method);
        CommandRegister.getInstance().process(command);
        expense = (Expense) command.getObject();
        return expense;
    }

    public ExpenseDTO getExpenseById(String id) throws NotFoundException, CommandException {
        Expense expense = (Expense) new InvoiceQueryHandler().getById(id);
        return Converters.convertToExpenseDTO(expense);
    }



    public List<Object> getPaymentTerms(HashMap<String, Object> requestData) {
        List<Object> lists = new PaymentTermQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }


    public RecurringInvoice createRecurringInvoice(HashMap<String, Object> requestData) throws Exception {


        CreateRecurringInvoice command = new CreateRecurringInvoice(requestData);
        CommandRegister.getInstance().process(command);
        RecurringInvoice recurringInvoice = (RecurringInvoice) command.getObject();

        return recurringInvoice;

    }

    public RecurringInvoice updateRecurringInvoice(HashMap<String, Object> requestData) throws Exception {


        UpdateRecurringInvoice command = new UpdateRecurringInvoice(requestData);
        CommandRegister.getInstance().process(command);
        RecurringInvoice recurringInvoice = (RecurringInvoice) command.getObject();

        return recurringInvoice;

    }

    public RecurringInvoiceDTO getRecurringInvoiceById(String id) throws NotFoundException, CommandException {
        RecurringInvoice recurringInvoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(id);
        return Converters.convertToRecurringInvoiceDTO(recurringInvoice);

    }


    public List<Object> getRecurringInvoices(HashMap<String, Object> requestData) {
        List<Object> lists = new RecurringInvoiceQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }


}

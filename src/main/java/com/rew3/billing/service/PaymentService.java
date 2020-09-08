package com.rew3.billing.service;

import com.rew3.billing.purchase.expense.command.CreateExpense;
import com.rew3.billing.purchase.expense.model.Expense;
import com.rew3.billing.purchase.expense.model.ExpenseDTO;
import com.rew3.billing.sale.invoice.InvoiceQueryHandler;
import com.rew3.billing.paymentterm.PaymentTermQueryHandler;
import com.rew3.billing.sale.invoice.RecurringInvoiceQueryHandler;
import com.rew3.billing.sale.invoice.command.*;
import com.rew3.billing.sale.invoice.model.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.Converters;

import java.util.HashMap;
import java.util.List;

public class PaymentService {
    public Invoice createCustomerInvoice(HashMap<String, Object> requestData) throws Exception {


        CreateCustomerInvoice command = new CreateCustomerInvoice(requestData);
        CommandRegister.getInstance().process(command);
        Invoice invoice = (Invoice) command.getObject();
        return invoice;

    }

    public Invoice updateCustomerInvoice(HashMap<String, Object> requestData) throws Exception {


        UpdateCustomerInvoice command = new UpdateCustomerInvoice(requestData);
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

    public PaymentTerm createUpdatePaymentTerm(HashMap<String, Object> requestData) throws Exception {


        CreateTerm command = new CreateTerm(requestData);
        CommandRegister.getInstance().process(command);
        PaymentTerm paymentTerm = (PaymentTerm) command.getObject();

        return paymentTerm;

    }

    public PaymentTermDTO getPaymentTermById(String id) throws NotFoundException, CommandException {
        PaymentTerm paymentTerm = (PaymentTerm) new PaymentTermQueryHandler().getById(id);
        return Converters.convertToPaymentTermDTO(paymentTerm);

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

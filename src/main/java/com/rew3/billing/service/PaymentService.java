package com.rew3.billing.service;

import com.rew3.purchase.expense.model.Expense;
import com.rew3.purchase.expense.model.ExpenseDTO;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.paymentterm.PaymentTermQueryHandler;
import com.rew3.sale.invoice.model.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.Converters;
import com.rew3.sale.recurringinvoice.RecurringInvoiceQueryHandler;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoiceDTO;

import java.util.HashMap;
import java.util.List;

public class PaymentService {


    public InvoiceDTO getInvoiceById(String id) throws NotFoundException, CommandException {
        Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(id);
        return Converters.convertToInvoiceDTO(invoice);

    }


    public List<Object> getInvoices(HashMap<String, Object> requestData) {
        List<Object> lists = new InvoiceQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }

    public ExpenseDTO getExpenseById(String id) throws NotFoundException, CommandException {
        Expense expense = (Expense) new InvoiceQueryHandler().getById(id);
        return Converters.convertToExpenseDTO(expense);
    }



    public List<Object> getPaymentTerms(HashMap<String, Object> requestData) {
        List<Object> lists = new PaymentTermQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
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

package com.rew3.payment.recurringinvoicepayment.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.INVOICEPAYMENT)
public class RecurringInvoicePayment extends AbstractEntity {

    @JoinColumn(name = DB.Field.RecurringInvoicePayment.RECURRING_INVOICE_ID)
    @ManyToOne
    private RecurringInvoice invoice;

    @JoinColumn(name = DB.Field.RecurringInvoicePayment.CUSTOMER_ID)
    @ManyToOne
    private Customer customer;

    @Column(name=DB.Field.RecurringInvoicePayment.AMOUNT)
    private Double amount;

    @Column(name = DB.Field.RecurringInvoicePayment.DATE)
    private Timestamp date;

    @Column(name = DB.Field.RecurringInvoicePayment.NOTES)
    private String notes;

    public RecurringInvoice getRecurringInvoice() {
        return invoice;
    }

    public void setRecurringInvoice(RecurringInvoice invoice) {
        this.invoice = invoice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
package com.rew3.payment.recurringinvoicepayment.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.RECURRING_INVOICE_PAYMENT)
public class RecurringInvoicePayment extends AbstractEntity {

    @NotNull(message = "Invoice must not be null")
    @JoinColumn(name = DB.Field.RecurringInvoicePayment.RECURRING_INVOICE_ID)
    @ManyToOne
    private RecurringInvoice invoice;

    @NotNull(message = "Customer id must not be null")
    @JoinColumn(name = DB.Field.RecurringInvoicePayment.CUSTOMER_ID)
    @ManyToOne
    private Customer customer;

    @NotNull(message = "Amount must not be null")
    @Column(name=DB.Field.RecurringInvoicePayment.AMOUNT)
    private Double amount;

    @NotNull(message = "Date must not be null")
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
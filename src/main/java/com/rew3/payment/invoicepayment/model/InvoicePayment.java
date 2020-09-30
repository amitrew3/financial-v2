package com.rew3.payment.invoicepayment.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.invoice.model.Invoice;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.INVOICEPAYMENT)
public class InvoicePayment extends AbstractEntity {

    @JoinColumn(name = DB.Field.InvoicePayment.INVOICE_ID)
    @ManyToOne
    private Invoice invoice;

    @JoinColumn(name = DB.Field.InvoicePayment.CUSTOMER_ID)
    @ManyToOne
    private Customer customer;

    @Column(name=DB.Field.InvoicePayment.AMOUNT)
    private Double amount;

    @Column(name = DB.Field.InvoicePayment.DATE)
    private Timestamp date;

    @Column(name = DB.Field.InvoicePayment.NOTES)
    private String notes;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
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
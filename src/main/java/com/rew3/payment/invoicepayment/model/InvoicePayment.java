package com.rew3.payment.invoicepayment.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.purchase.vendor.model.Vendor;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.invoice.model.Invoice;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.PAYMENT_OPTION)
public class InvoicePayment extends AbstractEntity {

    @JoinColumn(name = DB.Field.InvoicePayment.INVOICE_ID)
    @ManyToOne
    private Invoice invoice;

    @JoinColumn(name = DB.Field.InvoicePayment.CUSTOMER_ID)
    @ManyToOne
    private Customer customer;

    @Column(name=DB.Field.InvoicePayment.AMOUNT)
    private String description;

    @Column(name = DB.Field.InvoicePayment.DATE)
    private Timestamp date;

    @Column(name = DB.Field.InvoicePayment.NOTES)
    private Timestamp notes;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getNotes() {
        return notes;
    }

    public void setNotes(Timestamp notes) {
        this.notes = notes;
    }
}
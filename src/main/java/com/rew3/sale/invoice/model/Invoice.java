package com.rew3.sale.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.sale.customer.model.Customer;

import javax.persistence.*;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = DB.Table.INVOICE)
public class Invoice extends AbstractEntity {

    @Column(name = DB.Field.Invoice.INVOICE_NUMBER)
    private String invoiceNumber;

    @Column(name = DB.Field.Invoice.PO_SO_NUMBER)
    private String poSoNumber;



    @Column(name = DB.Field.Invoice.INVOICE_DATE)
    private Timestamp invoiceDate;

    @Column(name = DB.Field.Invoice.DUE_DATE)
    private Timestamp dueDate;

    @JoinColumn(name = DB.Field.Invoice.CUSTOMER_ID)
    @OneToOne
    private Customer customer;

    @JoinColumn(name = DB.Field.Invoice.PAYMENT_TERM_ID)
    @OneToOne
    private PaymentTerm paymentTerm;


    @Column(name = DB.Field.Invoice.MEMOS)
    private String memos;


    @Column(name = DB.Field.Invoice.PAYMENT_STATUS)
    private String paymentStatus;

    @Column(name = DB.Field.Invoice.SEND_DATE_TIME)
    private Timestamp sendDateTime;


    //Status of refund for invoice
    @Column(name = DB.Field.Invoice.INTERNAL_NOTES)
    private String internalNotes;

    @Column(name = DB.Field.Invoice.FOOTER_NOTES)
    private String footerNotes;

    @Column(name = DB.Field.Invoice.SUB_TOTAL)
    private Double subTotal;

    @Column(name = DB.Field.Invoice.TAX_TOTAL)
    private Double taxTotal;


    @Column(name = DB.Field.Invoice.TOTAL)
    private Double total;

    //Status of writeoff for invoice
    @Column(name = DB.Field.Invoice.BILLING_STREET)
    private String billingStreet;

    @Column(name = DB.Field.Invoice.BILLING_TOWN)
    private String billingTown;

    @Column(name = DB.Field.Invoice.BILLING_CITY)
    private Double billingCity;

    @Column(name = DB.Field.Invoice.BILLING_COUNTRY)
    private String billingCountry;

    @Column(name = DB.Field.Invoice.IS_DRAFT)
    private boolean isRecurring;


    @Valid
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true) 
    public Set<InvoiceItem> items;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }


    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDueDate() {
        return dueDate.toString();
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Flags.InvoicePaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus.toString();
    }


    public Set<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItem> items) {
        this.items = items;

    }
    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

}

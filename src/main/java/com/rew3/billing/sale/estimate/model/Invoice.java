package com.rew3.billing.sale.estimate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = DB.Table.INVOICE)
public class Invoice extends AbstractEntity {

    @Column(name = DB.Field.Invoice.INVOICE_NUMBER)
    private String invoiceNumber;

    @Column(name = DB.Field.Invoice.USER_ID)
    private String userId;


    @JoinColumn(name = DB.Field.Invoice.PAYMENT_TERM_ID)
    @OneToOne
    private PaymentTerm paymentTerm;

    @Column(name = DB.Field.Invoice.INVOICE_DATE)
    private Timestamp invoiceDate;

    @Column(name = DB.Field.Invoice.DUE_DATE)
    private Timestamp dueDate;

	
    @Column(name = DB.Field.Invoice.INVOICE_STATUS)

    private String invoiceStatus;
    //Status of invoice for sender in Internal invoice, recipient in External invoice

    //Status of invoice for recipient, used in Internal invoice

    //Status of payment for invoice
    @Column(name = DB.Field.Invoice.PAYMENT_STATUS)
    private String paymentStatus;

    //Status of due for invoice
    @Column(name = DB.Field.Invoice.DUE_STATUS)
    private String dueStatus;

    //Status of refund for invoice
    @Column(name = DB.Field.Invoice.REFUND_STATUS)
    private String refundStatus;

    //Status of writeoff for invoice
    @Column(name = DB.Field.Invoice.WRITE_OFF_STATUS)
    private String writeOffStatus;

    @Column(name = DB.Field.Invoice.TAX_TYPE)
    private String taxType;

    @Column(name = DB.Field.Invoice.TAX)
    private Double tax;

    @Column(name = DB.Field.Invoice.DISCOUNT_TYPE)
    private String discountType;

    @Column(name = DB.Field.Invoice.DISCOUNT)
    private Double discount;

    @Column(name = DB.Field.Invoice.NOTE)
    private String note;

    @Column(name = DB.Field.Invoice.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.Invoice.DATA)
    private String data;

    @Column(name = DB.Field.Invoice.TYPE)
    private String type;

    @Column(name = DB.Field.Invoice.IS_RECURRING_INVOICE)
    private boolean isRecurring;

    @JoinColumn (name = DB.Field.Invoice.RECURRING_INVOICE_ID)
    @ManyToOne
    private RecurringInvoice recurringInvoice;

    @Column(name = DB.Field.Invoice.TOTAL_AMOUNT)
    private Double totalAmount;

    @Column(name = DB.Field.Invoice.DUE_AMOUNT)
    private Double dueAmount;


    @Valid
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true) 
    public Set<InvoiceItem> items;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice")
    public Set<InvoiceReference> reference;


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


    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }





    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Flags.InvoicePaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus.toString();
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Flags.InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus.toString();
    }

    public String getDueStatus() {
        return dueStatus;
    }

    public void setDueStatus(Flags.InvoiceDueStatus dueStatus) {
        this.dueStatus = dueStatus.toString();
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Flags.InvoiceRefundStatus refundStatus) {
        this.refundStatus = refundStatus.toString();
    }

    public String getWriteOffStatus() {
        return writeOffStatus;
    }

    public void setWriteOffStatus(Flags.InvoiceWriteOffStatus writeoffStatus) {
        this.writeOffStatus = writeoffStatus.toString();
    }



    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Flags.CalculationType discountType) {
        this.discountType = discountType.toString();
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(Flags.InvoiceType type) {
        this.type = type.toString();
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

    public void setTaxType(Flags.CalculationType taxType) {
        this.taxType = taxType.toString();
    }



    public String getTaxType() {
        return taxType;
    }




    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public RecurringInvoice getRecurringInvoice() {
        return recurringInvoice;
    }

    public void setRecurringInvoice(RecurringInvoice recurringInvoice) {
        this.recurringInvoice = recurringInvoice;
    }

    public Set<InvoiceReference> getReference() {
        return reference;
    }

    public void setReference(Set<InvoiceReference> reference) {
        this.reference = reference;
    }
}

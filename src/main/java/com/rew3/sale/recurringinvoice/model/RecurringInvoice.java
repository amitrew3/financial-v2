package com.rew3.sale.recurringinvoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.shared.model.Address;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.sale.customer.model.Customer;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = DB.Table.RECURRING_INVOICE)
public class RecurringInvoice  extends AbstractEntity {

    @Column(name = DB.Field.RecurringInvoice.INVOICE_NUMBER)
    private String invoiceNumber;

    @Column(name = DB.Field.Invoice.PO_SO_NUMBER)
    private String poSoNumber;

    @NotNull(message = "Invoice date must not be null")
    @Column(name = DB.Field.RecurringInvoice.INVOICE_DATE)
    private Timestamp invoiceDate;

    @Column(name = DB.Field.RecurringInvoice.DUE_DATE)
    private Timestamp dueDate;

    @NotNull(message = "Customer Id must not be null")
    @JoinColumn(name = DB.Field.RecurringInvoice.CUSTOMER_ID)
    @OneToOne
    private Customer customer;

    @JoinColumn(name = DB.Field.RecurringInvoice.PAYMENT_TERM_ID)
    @OneToOne
    private PaymentTerm paymentTerm;

    @JoinColumn(name = DB.Field.RecurringInvoice.RECURRING_SCHEDULE_ID)
    @OneToOne
    private RecurringSchedule recurringSchedule;

    @JoinColumn(name = DB.Field.RecurringInvoice.RECURRING_TEMPALTE_ID)
    @OneToOne
    private RecurringTemplate recurringTemplate;


    @Column(name = DB.Field.RecurringInvoice.MEMOS)
    private String memos;


    @Column(name = DB.Field.RecurringInvoice.PAYMENT_STATUS)
    private String paymentStatus;

    @Column(name = DB.Field.RecurringInvoice.SEND_DATE_TIME)
    private Timestamp sendDateTime;


    @Column(name = DB.Field.RecurringInvoice.INTERNAL_NOTES)
    private String internalNotes;

    @Column(name = DB.Field.RecurringInvoice.FOOTER_NOTES)
    private String footerNotes;

    @Column(name = DB.Field.RecurringInvoice.SUB_TOTAL)
    private Double subTotal;

    @Column(name = DB.Field.RecurringInvoice.TAX_TOTAL)
    private Double taxTotal;


    @Column(name = DB.Field.RecurringInvoice.TOTAL)
    private Double total;

    //Status of writeoff for invoice
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "town", column = @Column(name = "billing_town")),
            @AttributeOverride(name = "province", column = @Column(name = "billing_province")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "billing_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")),
    })
    Address address;


    @Column(name = DB.Field.RecurringInvoice.IS_SENT)
    private boolean isSent;


    @Valid
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recurringInvoice", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<RecurringInvoiceItem> items;


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

    public Timestamp getDueDate() {
        return dueDate;
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


    public Set<RecurringInvoiceItem> getItems() {
        return items;
    }

    public void setItems(Set<RecurringInvoiceItem> items) {
        this.items = items;

    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getPoSoNumber() {
        return poSoNumber;
    }

    public void setPoSoNumber(String poSoNumber) {
        this.poSoNumber = poSoNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getMemos() {
        return memos;
    }

    public void setMemos(String memos) {
        this.memos = memos;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(Timestamp sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public String getInternalNotes() {
        return internalNotes;
    }

    public void setInternalNotes(String internalNotes) {
        this.internalNotes = internalNotes;
    }

    public String getFooterNotes() {
        return footerNotes;
    }

    public void setFooterNotes(String footerNotes) {
        this.footerNotes = footerNotes;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(Double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public RecurringSchedule getRecurringSchedule() {
        return recurringSchedule;
    }

    public void setRecurringSchedule(RecurringSchedule recurringSchedule) {
        this.recurringSchedule = recurringSchedule;
    }

    public RecurringTemplate getRecurringTemplate() {
        return recurringTemplate;
    }

    public void setRecurringTemplate(RecurringTemplate recurringTemplate) {
        this.recurringTemplate = recurringTemplate;
    }
}

package com.rew3.billing.sale.invoice.model;


import java.util.Set;


public class InvoiceDTO extends AbstractDTO {


    private InvoiceInfo invoiceInfo;

    private Set<InvoiceItem> items;

    private String userId;

    private PaymentTerm paymentTerm;

    private String invoiceDate;

    private String dueDate;

    private String data;

    private String type;

    private boolean isRecurringInvoice;

    private RecurringInvoice recurringInvoice;

    private Double totalAmount;

    private Double dueAmount;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRecurringInvoice() {
        return isRecurringInvoice;
    }

//    public void setRecurringInvoice(boolean recurringInvoice) {
//        isRecurringInvoice = recurringInvoice;
//    }

    public RecurringInvoice getRecurringInvoice() {
        return recurringInvoice;
    }

    public void setRecurringInvoice(RecurringInvoice recurringInvoice) {
        this.recurringInvoice = recurringInvoice;
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

    public Set<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItem> items) {
        this.items = items;
    }

    public InvoiceInfo getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }
}



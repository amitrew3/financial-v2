package com.rew3.sale.estimate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.sale.customer.model.Customer;

import javax.persistence.*;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = DB.Table.ESTIMATE)
public class Estimate extends AbstractEntity {
    @Column(name = DB.Field.Estimate.ESTIMATE_NUMBER)
    private String estimateNumber;

    @Column(name = DB.Field.Estimate.PO_SO_NUMBER)
    private String poSoNumber;

    @Column(name = DB.Field.Estimate.ESTIMATE_DATE)
    private Timestamp estimateDate;

    @JoinColumn(name = DB.Field.Estimate.CUSTOMER_ID)
    @OneToOne
    private Customer customer;

    @JoinColumn(name = DB.Field.Estimate.PAYMENT_TERM_ID)
    @OneToOne
    private PaymentTerm paymentTerm;


    @Column(name = DB.Field.Estimate.NOTES)
    private String notes;

    @Column(name = DB.Field.Estimate.SUB_TOTAL)
    private Double subTotal;

    @Column(name = DB.Field.Estimate.TAX_TOTAL)
    private Double taxTotal;

    @Column(name = DB.Field.Estimate.TOTAL)
    private Double total;

    @Valid
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "estimate", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<EstimateItem> items;

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public String getPoSoNumber() {
        return poSoNumber;
    }

    public void setPoSoNumber(String poSoNumber) {
        this.poSoNumber = poSoNumber;
    }

    public Timestamp getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Timestamp estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Set<EstimateItem> getItems() {
        return items;
    }

    public void setItems(Set<EstimateItem> items) {
        this.items = items;
    }

}

package com.rew3.purchase.bill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.purchase.vendor.model.Vendor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = DB.Table.BILL)
public class Bill extends AbstractEntity {

    @Column(name = DB.Field.Bill.BILL_NUMBER)
    private String billNumber;

    @Column(name = DB.Field.Bill.PO_SO_NUMBER)
    private String poSoNumber;

    @NotNull(message = "Invoice Date must not be null")
    @Column(name = DB.Field.Bill.BILL_DATE)
    private Timestamp billDate;

    @Column(name = DB.Field.Bill.DUE_DATE)
    private Timestamp dueDate;

    @NotNull(message = "Vendor Id must not be null")
    @JoinColumn(name = DB.Field.Bill.VENDOR_ID)
    @OneToOne
    private Vendor vendor;


    @Column(name = DB.Field.Bill.NOTES)
    private String notes;

    @Column(name = DB.Field.Bill.SUB_TOTAL)
    private Double subTotal;

    @Column(name = DB.Field.Bill.TAX_TOTAL)
    private Double taxTotal;


    @Column(name = DB.Field.Bill.TOTAL)
    private Double total;


    @Valid
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<BillItem> items;



    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getPoSoNumber() {
        return poSoNumber;
    }

    public void setPoSoNumber(String poSoNumber) {
        this.poSoNumber = poSoNumber;
    }

    public Timestamp getBillDate() {
        return billDate;
    }

    public void setBillDate(Timestamp billDate) {
        this.billDate = billDate;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

    public Set<BillItem> getItems() {
        return items;
    }

    public void setItems(Set<BillItem> items) {
        this.items = items;
    }
}

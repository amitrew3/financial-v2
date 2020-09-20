package com.rew3.payment.billpayment.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.purchase.vendor.model.Vendor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.BILLPAYMENT)
public class BillPayment extends AbstractEntity {

    @JoinColumn(name = DB.Field.BillPayment.BILL_ID)
    @ManyToOne
    private Bill bill;

    @JoinColumn(name = DB.Field.BillPayment.VENDOR_ID)
    @ManyToOne
    private Vendor vendor;

    @Column(name=DB.Field.BillPayment.AMOUNT)
    private Double amount;

    @Column(name = DB.Field.BillPayment.DATE)
    private Timestamp date;

    @Column(name = DB.Field.BillPayment.NOTES)
    private Timestamp notes;

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
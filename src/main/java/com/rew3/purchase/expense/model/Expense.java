package com.rew3.purchase.expense.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = DB.Table.EXPENSE)
public class Expense extends AbstractEntity {

    @Column(name = DB.Field.Expense.TITLE)
    private String expenseNumber;

    @Column(name = DB.Field.Expense.DATE)
    private Timestamp date;

    @Column(name = DB.Field.Expense.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.Expense.MERCHANT)
    private String merchant;

    @Column(name = DB.Field.Expense.NOTES)
    private String notes;

    @Column(name = DB.Field.Expense.CURRENCY)
    private String currency;

    @Column(name = DB.Field.Expense.TOTAL)
    private String total;

    public String getExpenseNumber() {
        return expenseNumber;
    }

    public void setExpenseNumber(String expenseNumber) {
        this.expenseNumber = expenseNumber;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}


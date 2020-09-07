package com.rew3.billing.purchase.expense.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = DB.Table.EXPENSE)
public class Expense extends AbstractEntity {

    @Column(name = DB.Field.Expense.EXPENSE_NUMBER)
    private String expenseNumber;

    @Column(name = DB.Field.Expense.EXPENSE_DATE)
    private Timestamp expenseDate;

    @Column(name = DB.Field.Expense.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.Expense.VENDOR_ID)
    private String vendorId;

    @Column(name = DB.Field.Expense.PAY_FROM_ACCOUNT_ID)
    private String payFromAccount;

    @Column(name = DB.Field.Expense.CATEGORY_EXPENSE_ACCOUNT)
    private String categoryExpenseAccount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "expense")
    public Set<ExpenseItem> items;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "expense")
    public Set<ExpenseReference> reference;

    @Column(name = DB.Field.Expense.AMOUNT)
    private Double amount;


    public String getExpenseNumber() {
        return expenseNumber;
    }

    public void setExpenseNumber(String expenseNumber) {
        this.expenseNumber = expenseNumber;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getPayFromAccount() {
        return payFromAccount;
    }

    public void setPayFromAccount(String payFromAccount) {
        this.payFromAccount = payFromAccount;
    }

    public String getCategoryExpenseAccount() {
        return categoryExpenseAccount;
    }

    public void setCategoryExpenseAccount(String categoryExpenseAccount) {
        this.categoryExpenseAccount = categoryExpenseAccount;
    }

    public Set<ExpenseItem> getItems() {
        return items;
    }

    public void setItems(Set<ExpenseItem> items) {
        this.items = items;
    }

    public String getExpenseDate() {
        return expenseDate.toString();
    }

    public void setExpenseDate(Timestamp expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}


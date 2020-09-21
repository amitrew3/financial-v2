package com.rew3.purchase.expense.model;


import com.rew3.sale.invoice.model.AbstractDTO;

import java.util.List;


public class ExpenseDTO extends AbstractDTO {

    private String expenseNumber;

    private String expenseDate;

    private String description;

    private String vendorId;

    private String payFromAccount;

    private String categoryExpenseAccount;

    private Double amount;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getExpenseNumber() {
        return expenseNumber;
    }

    public void setExpenseNumber(String expenseNumber) {
        this.expenseNumber = expenseNumber;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
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
}



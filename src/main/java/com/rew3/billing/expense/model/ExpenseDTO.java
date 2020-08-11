package com.rew3.billing.expense.model;


import com.rew3.billing.invoice.model.AbstractDTO;

import java.util.List;


public class ExpenseDTO extends AbstractDTO {

    private List<ExpenseItem> items;
    private String expenseNumber;

    private String expenseDate;

    private String description;

    private String vendorId;

    private String payFromAccount;

    private String categoryExpenseAccount;

    private Double amount;

    public List<ExpenseReference> reference;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<ExpenseReference> getReference() {
        return reference;
    }

    public void setReference(List<ExpenseReference> reference) {
        this.reference = reference;
    }

    public List<ExpenseItem> getItems() {
        return items;
    }

    public void setItems(List<ExpenseItem> items) {
        this.items = items;
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



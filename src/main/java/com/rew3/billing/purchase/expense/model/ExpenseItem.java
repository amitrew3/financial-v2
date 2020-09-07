package com.rew3.billing.purchase.expense.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.EXPENSE_ITEM)
public class ExpenseItem {

    @Id
    @JsonIgnore
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")

    @Column(name = DB.Field.InvoiceItem.ID, updatable = false)
    private String id;

    @Column(name = DB.Field.ExpenseItem.TITLE)
    private String name;

    @Column(name = DB.Field.ExpenseItem.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.ExpenseItem.QUANTITY)
    private Double quantity;


    @Column(name = DB.Field.ExpenseItem.PRICE)
    private Double price;

    @JsonIgnore
    @JoinColumn(name = DB.Field.InvoiceItem.INVOICE_ID)
    @ManyToOne
    private Expense expense;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

}


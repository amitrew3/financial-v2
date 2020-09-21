package com.rew3.accounting.transaction.model;

import com.rew3.accounting.account.model.Account;
import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.TRANSACTION)
public class Transaction extends AbstractEntity {


    @Column(name = DB.Field.Transaction.DATE)
    private Timestamp date;

    @Column(name = DB.Field.Transaction.AMOUNT)
    private Double amount;

    @Column(name = DB.Field.Transaction.DESCRIPTION)
    private String description;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

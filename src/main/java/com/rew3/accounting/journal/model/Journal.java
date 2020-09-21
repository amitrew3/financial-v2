package com.rew3.accounting.journal.model;

import com.rew3.accounting.account.model.Account;
import com.rew3.accounting.accountperiod.model.AccountPeriod;
import com.rew3.accounting.transaction.model.Transaction;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.JOURNAL)
public class Journal extends AbstractEntity {


    @JoinColumn(name = DB.Field.Journal.ACCOUNT_ID)
    @ManyToOne
    private Account account;

    @JoinColumn(name = DB.Field.Journal.TRANSACTION_ID)
    @ManyToOne
    private Transaction transaction;

    @Column(name = DB.Field.Journal.DATE)
    private Timestamp date;

    @Column(name = DB.Field.Journal.AMOUNT)
    private Double amount;

    @JoinColumn(name = "accounting_period_id")
    @ManyToOne
    private AccountPeriod accountPeriod;

    @Column(name = DB.Field.Journal.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.Journal.SIDE)
    private String side;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

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

    public AccountPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(AccountPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSide() {
        return side;
    }

    public void setSide(Flags.AccountSide side) {
        this.side = side.toString();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setSide(String side) {
        this.side = side;
    }
}

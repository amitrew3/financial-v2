package com.rew3.accounting.transaction.model;

import com.rew3.common.model.Flags.AccountingCodeSegment;

import java.util.List;

public class TrialBalanceEntry {
    String accountingCode;
    String accountingCodeType;
    AccountingCodeSegment segment;
    double debit;
    double credit;
    List<Transaction> transactionList;

    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(String accountingCode) {
        this.accountingCode = accountingCode;
    }

    public String getAccountingCodeType() {
        return accountingCodeType;
    }

    public void setAccountingCodeType(String accountingCodeType) {
        this.accountingCodeType = accountingCodeType;
    }

    public AccountingCodeSegment getSegment() {
        return segment;
    }

    public void setSegment(AccountingCodeSegment segment) {
        this.segment = segment;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}

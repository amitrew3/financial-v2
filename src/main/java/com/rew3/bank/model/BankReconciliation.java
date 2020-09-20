/*
package com.rew3.payment.invoicepayment.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.accounting.accountingperiod.model.AccountPeriod;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.BANK_RECONCILIATION)
public class BankReconciliation extends AbstractEntity {

    @JoinColumn(name = "billing_account_id")
    @ManyToOne
    private BillingAccount billingAccount;

    @JoinColumn(name = "accounting_period_id")
    @ManyToOne
    private AccountPeriod accountPeriod;

    @Column(name = "statement_end_date")
    private Timestamp endDate;

    @Column(name = "statement_end_balance")
    private Double endStatementBalance;

    @Column(name = "checkbook_end_balance")
    private Double endCheckbookBalance;

   */
/* public Long getBillingAccountId() {
        return billingAccountId;
    }

    public void setBillingAccountId(Long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }*//*


    public String getEndDate() {
        return endDate.toString();
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Double getEndStatementBalance() {
        return endStatementBalance;
    }

    public void setEndStatementBalance(Double endBalance) {
        this.endStatementBalance = endBalance;
    }

    public Double getEndCheckbookBalance() {
        return endCheckbookBalance;
    }

    public void setEndCheckbookBalance(Double endCheckbookBalance) {
        this.endCheckbookBalance = endCheckbookBalance;
    }

   */
/* public Long getAccountingPeriodId() {
        return accountingPeriodId;
    }

    public void setAccountingPeriodId(Long accountingPeriodId) {
        this.accountingPeriodId = accountingPeriodId;
    }*//*


    public BillingAccount getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
    }

    public AccountPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(AccountPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }
}

*/

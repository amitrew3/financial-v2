package com.rew3.finance.accountingperiod.model;


import com.rew3.billing.payment.model.BillingAccount;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.ACCOUNTING_PERIOD_REQUEST)
public class AccountingPeriodRequest extends AbstractEntity {


    @JoinColumn(name = "accounting_period_id")
    @ManyToOne
    private AccountingPeriod accountingPeriod;

    @JoinColumn(name = "billing_account_id")
    @ManyToOne
    private BillingAccount billingAccount;

    @Column(name = "actor_id")
    private String actorId;

    @Column(name = "request_status")
    private Flags.AccountingPeriodRequestStatus requestStatus;

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

   /* public Long getBillingAccountId() {
        return billingAccountId;
    }

    public void setBillingAccountId(Long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }*/

    public Flags.AccountingPeriodRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Flags.AccountingPeriodRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public AccountingPeriod getAccountingPeriod() {
        return accountingPeriod;
    }

    public void setAccountingPeriod(AccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public BillingAccount getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
    }
}

package com.rew3.billing.payment.model;


import com.rew3.billing.normaluser.model.NormalUser;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.TrustTxnMemoType;
import com.rew3.finance.accountingperiod.model.AccountingPeriod;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.BANK_TRANSACTION)
public class BankTransaction extends AbstractEntity {

    @JoinColumn(name = "billing_account_id")
    @ManyToOne
    private BillingAccount account;

    @Column(name = "txn_name")
    private String txnName;


    @Column(name = "txn_date")
    private Timestamp txnDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Flags.BankTransactionType type;

    @Column(name = "reference")
    private String reference;

    @Column(name = "memo")
    private String memo;

    @Column(name = "amount")
    private Double amount;

    @JoinColumn(name = "contact_id")
    @ManyToOne
    private NormalUser contact;

    @Column(name = "reconciliation_status")
    @Enumerated(EnumType.STRING)
    private Flags.ReconciliationStatus reconciliationStatus;

    @Column(name = "clearance_status")
    @Enumerated(EnumType.STRING)
    private Flags.ClearanceStatus clearanceStatus;

    @JoinColumn(name = "bank_reconciliation_id")
    @ManyToOne
    private BankReconciliation bankReconciliation;

    @JoinColumn(name = "accounting_period_id")
    @ManyToOne
    private AccountingPeriod accountingPeriod;

    @Column(name = "txn_cleared_date")
    private Timestamp txnClearedDate;

    @JoinColumn(name = "cleared_txn_period_id")
    @ManyToOne
    private AccountingPeriod clearedTransactionPeriod;


    //Trust Transaction fields
    @Column(name = "listing_name")
    private String listingName;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "payer")
    private String payer;

    @Column(name = "trust_txn_memo_type")
    @Enumerated(EnumType.STRING)
    private TrustTxnMemoType memoType;



    public Timestamp getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Timestamp txnDate) {
        this.txnDate = txnDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTxnName() {
        return txnName;
    }

    public void setTxnName(String txnName) {
        this.txnName = txnName;
    }

    public Flags.ReconciliationStatus getReconciliationStatus() {
        return reconciliationStatus;
    }


    public void setReconciliationStatus(Flags.ReconciliationStatus reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

    public Flags.ClearanceStatus getClearanceStatus() {
        return clearanceStatus;
    }

    public void setClearanceStatus(Flags.ClearanceStatus clearanceStatus) {
        this.clearanceStatus = clearanceStatus;
    }


    public Timestamp getTxnClearedDate() {
        return txnClearedDate;
    }

    public void setTxnClearedDate(Timestamp txnClearedDate) {
        this.txnClearedDate = txnClearedDate;
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public TrustTxnMemoType getMemoType() {
        return memoType;
    }

    public void setMemoType(TrustTxnMemoType memoType) {
        this.memoType = memoType;
    }


    public BillingAccount getAccount() {
        return account;
    }

    public void setAccount(BillingAccount account) {
        this.account = account;
    }

    public BankReconciliation getBankReconciliation() {
        return bankReconciliation;
    }

    public void setBankReconciliation(BankReconciliation bankReconciliation) {
        this.bankReconciliation = bankReconciliation;
    }

    public AccountingPeriod getAccountingPeriod() {
        return accountingPeriod;
    }

    public void setAccountingPeriod(AccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public AccountingPeriod getClearedTransactionPeriod() {
        return clearedTransactionPeriod;
    }

    public void setClearedTransactionPeriod(AccountingPeriod clearedTransactionPeriod) {
        this.clearedTransactionPeriod = clearedTransactionPeriod;
    }

    public void setType(Flags.BankTransactionType type) {
        this.type = type;
    }

    public Flags.BankTransactionType getType() {
        return type;
    }

    public NormalUser getContact() {
        return contact;
    }

    public void setContact(NormalUser contact) {
        this.contact = contact;
    }
}

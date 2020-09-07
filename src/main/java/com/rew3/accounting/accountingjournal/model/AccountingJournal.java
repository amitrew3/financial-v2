package com.rew3.accounting.accountingjournal.model;

import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.accounting.accountingcode.model.AccountingCode;
import com.rew3.accounting.accountingperiod.model.AccountingPeriod;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.ACCOUNTING_JOURNAL)
public class AccountingJournal extends AbstractEntity {


    @Column(name = "is_debit")
    private boolean isDebit;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "segment")
    @Enumerated(EnumType.STRING)
    private Flags.AccountingCodeSegment segment;

    @JoinColumn(name = "accounting_period_id")
    @ManyToOne
    private AccountingPeriod accountingPeriod;

    @JoinColumn(name = "accounting_code_id")
    @ManyToOne
    private AccountingCode accountingCode;

    @Column(name = "entry_number")
    private Integer entryNumber;

   /* @Column(name = "debit")
    private Double debit;
*/
    @Column(name = "amount")
    private Double amount;

    @Column(name = "ref_id")
    private String refId;

    @Column(name = "ref_type")
    private Byte refType;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Flags.AccountingCodeSegment getSegment() {
        return segment;
    }

    public void setSegment(Flags.AccountingCodeSegment segment) {
        this.segment = segment;
    }

    public Integer getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(Integer entryNumber) {
        this.entryNumber = entryNumber;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Byte getRefType() {
        return refType;
    }

    public void setRefType(Byte refType) {
        this.refType = refType;
    }

    public AccountingPeriod getAccountingPeriod() {
        return accountingPeriod;
    }

    public void setAccountingPeriod(AccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public AccountingCode getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(AccountingCode accountingCode) {
        this.accountingCode = accountingCode;
    }

    public boolean isDebit() {
        return isDebit;
    }

    public void setDebit(boolean isDebit) {
        this.isDebit = isDebit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

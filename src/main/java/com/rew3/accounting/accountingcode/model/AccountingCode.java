package com.rew3.accounting.accountingcode.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.ACCOUNTING_CODE)
public class AccountingCode extends AbstractEntity {

    @Column(name = DB.Field.AccountingCode.CODE)
    private String code;

    @Column(name = DB.Field.AccountingCode.NAME)
    private String name;

    @JoinColumn(name = DB.Field.AccountingCode.SUB_ACCOUNTING_HEAD_ID)
    @ManyToOne
    private SubAccountingHead subAccountingHead;

    @Column(name = DB.Field.AccountingCode.SEGMENT)
    private String segment;

    @Column(name = DB.Field.AccountingCode.NOTE)
    private String note;

    @Column(name = DB.Field.AccountingCode.IS_DEFAULT)
    private boolean isDefault = false;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }


    public SubAccountingHead getSubAccountingHead() {
        return subAccountingHead;
    }

    public void setSubAccountingHead(SubAccountingHead subAccountingHead) {
        this.subAccountingHead = subAccountingHead;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(Flags.AccountingCodeSegment segment) {
        this.segment = segment.toString();
    }
}

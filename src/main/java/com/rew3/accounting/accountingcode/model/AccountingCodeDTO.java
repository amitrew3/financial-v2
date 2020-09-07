package com.rew3.accounting.accountingcode.model;


import com.rew3.billing.sale.invoice.model.AbstractDTO;


public class AccountingCodeDTO extends AbstractDTO {
    private String code;

    private String name;

    private SubAccountingHead subAccountingHead;

    private String segment;

    private String note;

    private boolean isDefault = false;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubAccountingHead getSubAccountingHead() {
        return subAccountingHead;
    }

    public void setSubAccountingHead(SubAccountingHead subAccountingHead) {
        this.subAccountingHead = subAccountingHead;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}



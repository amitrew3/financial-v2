package com.rew3.finance.accountingcode.model;


import com.rew3.billing.invoice.model.AbstractDTO;
import com.rew3.billing.shared.model.MiniUser;
import com.rew3.commission.commissionplan.model.*;
import com.rew3.common.model.DB;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Set;


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



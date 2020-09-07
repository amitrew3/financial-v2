package com.rew3.accounting.accountingcode.model;

import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.SUB_ACCOUNTING_HEAD)
public class SubAccountingHead extends AbstractEntity {


    @Column(name = DB.Field.SubAccountingHead.ACCOUNTING_CODE_TYPE)
    private String accountingCodeType;

    @Column(name = DB.Field.SubAccountingHead.ACCOUNTING_HEAD)
    private String accountingHead;

    @Column(name = DB.Field.SubAccountingHead.CODE)
    private Integer code;

    @Column(name = DB.Field.SubAccountingHead.DESCRIPTION)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getAccountingCodeType() {
        return accountingCodeType;
    }

    public void setAccountingCodeType(String accountingCodeType) {
        this.accountingCodeType = accountingCodeType;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAccountingHead() {
        return accountingHead;
    }

    public void setAccountingHead(Flags.AccountingHead accountingHead) {
        this.accountingHead = accountingHead.toString();
    }
}

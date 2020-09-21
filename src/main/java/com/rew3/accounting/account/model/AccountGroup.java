package com.rew3.accounting.account.model;

import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = DB.Table.ACCOUNTGROUP)
public class AccountGroup extends AbstractEntity {


    @Column(name = DB.Field.AccountGroup.TITLE)
    private String accountingCodeType;

    @Column(name = DB.Field.AccountGroup.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.AccountGroup.ACCOUNT_HEAD)
    private String accountingHead;

    @Column(name = DB.Field.AccountGroup.CODE)
    private Integer code;

    public String getAccountingCodeType() {
        return accountingCodeType;
    }

    public void setAccountingCodeType(String accountingCodeType) {
        this.accountingCodeType = accountingCodeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountingHead() {
        return accountingHead;
    }

    public void setAccountingHead(Flags.AccountingHead accountingHead) {
        this.accountingHead = accountingHead.toString();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

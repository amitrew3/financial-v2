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
    private String title;

    @Column(name = DB.Field.AccountGroup.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.AccountGroup.ACCOUNT_HEAD)
    private String accountHead;

    @Column(name = DB.Field.AccountGroup.CODE)
    private Integer code;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(Flags.AccountingHead accountHead) {
        this.accountHead = accountHead.toString();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAccountHead(String accountHead) {
        this.accountHead = accountHead;
    }
}

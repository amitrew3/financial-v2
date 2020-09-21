package com.rew3.accounting.account.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.ACCOUNT)
public class Account extends AbstractEntity {

    @Column(name = DB.Field.Account.CODE)
    private String code;

    @Column(name = DB.Field.Account.TITLE)
    private String title;

    @Column(name = DB.Field.Account.DESCRIPTION)
    private String description;

    @JoinColumn(name = DB.Field.Account.ACCOUNT_GROUP_ID)
    @ManyToOne
    private AccountGroup accountGroup;


    @Column(name = DB.Field.Account.ACCOUNT_HEAD)
    private String accountHead;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountGroup getAccountGroup() {
        return accountGroup;
    }

    public void setAccountGroup(AccountGroup accountGroup) {
        this.accountGroup = accountGroup;
    }

    public String getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(String accountHead) {
        this.accountHead = accountHead;
    }
}

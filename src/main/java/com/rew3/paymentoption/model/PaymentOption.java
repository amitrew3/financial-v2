package com.rew3.paymentoption.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = DB.Table.PAYMENT_OPTION)
public class PaymentOption extends AbstractEntity {

    @Column(name=DB.Field.PaymentOption.TITLE)
    private String name;

    @Column(name=DB.Field.PaymentOption.DESCRIPTION)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
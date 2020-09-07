package com.rew3.billing.sale.invoice.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = DB.Table.PAYMENT_TERM)
public class PaymentTerm extends AbstractEntity {

    @Column(name = DB.Field.PaymentTerm.NAME)
    private String name;

    @Column(name = DB.Field.PaymentTerm.VALUE)
    private Integer value;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
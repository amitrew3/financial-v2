package com.rew3.paymentterm.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = DB.Table.PAYMENT_TERM)
public class PaymentTerm extends AbstractEntity {


    @NotNull(
            message = "Title must not be null"

    )
    @Column(name = DB.Field.PaymentTerm.TITLE)
    private String title;
    @NotNull(
            message = "Value must not be null"

    )
    @Column(name = DB.Field.PaymentTerm.VALUE)
    private Integer value;
    @Column(name = DB.Field.PaymentTerm.DESCRIPTION)
    private String description;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
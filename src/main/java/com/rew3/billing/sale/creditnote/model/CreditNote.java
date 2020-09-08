package com.rew3.billing.sale.creditnote.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = DB.Table.CREDIT_NOTE)
public class CreditNote extends AbstractEntity {


    @Column(name = "name")
    private String name;

    @Column(name = "description")
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

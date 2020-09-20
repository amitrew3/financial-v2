package com.rew3.salestax.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = DB.Table.SALESTAX)
public class SalesTax extends AbstractEntity {

    @Column(name = DB.Field.SalesTax.TITLE)
    private String title;

    @Column(name = DB.Field.SalesTax.ABBREVIATION)
    private String value;
    @Column(name = DB.Field.SalesTax.DESCRIPTION)
    private String description;
    @Column(name = DB.Field.SalesTax.TAX_NUMBER)
    private String taxNumber;
    @Column(name = DB.Field.SalesTax.SHOW_TAX_NUMBER)
    private boolean showTaxNumber;
    @Column(name = DB.Field.SalesTax.RATE)
    private Double rate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public boolean isShowTaxNumber() {
        return showTaxNumber;
    }

    public void setShowTaxNumber(boolean showTaxNumber) {
        this.showTaxNumber = showTaxNumber;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
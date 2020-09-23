package com.rew3.catalog.product.model;

import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.salestax.model.SalesTax;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.PRODUCT)
public class Product extends AbstractEntity {


    @Column(name = DB.Field.Product.TITLE)
    private String title;

    @Column(name = DB.Field.Product.DESCRIPTION)
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = DB.Field.Product.SIDE)
    private String side;
    @OneToOne
    @JoinColumn(name = DB.Field.InvoiceItem.TAX1)
    private SalesTax tax1;

    @OneToOne
    @JoinColumn(name = DB.Field.InvoiceItem.TAX2)
    private SalesTax tax2;

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SalesTax getTax1() {
        return tax1;
    }

    public void setTax1(SalesTax tax1) {
        this.tax1 = tax1;
    }

    public SalesTax getTax2() {
        return tax2;
    }

    public void setTax2(SalesTax tax2) {
        this.tax2 = tax2;
    }
}

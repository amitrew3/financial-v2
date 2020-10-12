package com.rew3.catalog.product.model;

import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.salestax.model.SalesTax;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = DB.Table.PRODUCT)
public class Product extends AbstractEntity {

    @NotNull(
            message = "Title must not be null"

    )
    @Column(name = DB.Field.Product.TITLE)
    private String title;

    @NotNull(
            message = "Price must not be null"

    )
    @Column(name = DB.Field.Product.PRICE)
    private Double price;

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
    @JoinColumn(name = DB.Field.Product.TAX1)
    private SalesTax tax1;

    @OneToOne
    @JoinColumn(name = DB.Field.Product.TAX2)
    private SalesTax tax2;

    public String getSide() {
        return side;
    }

    public void setSide(Flags.ProductSide side) {
        this.side = side.toString();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

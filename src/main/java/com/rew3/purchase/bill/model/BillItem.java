package com.rew3.purchase.bill.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.sale.invoice.model.Invoice;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = DB.Table.BILL_ITEM)
public class BillItem {


    @Id
    @JsonIgnore
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")

    @Column(name = DB.Field.BillItem.ID, updatable = false)
    private String id;


    @JsonIgnore
    @JoinColumn(name = DB.Field.BillItem.BILL_ID)
    @ManyToOne
    private Invoice invoice;

    @JsonIgnore
    @JoinColumn(name = DB.Field.BillItem.PRODUCT_ID)
    @ManyToOne
    private Product product;

    @NotNull
    @Column(name = DB.Field.BillItem.QUANTITY)
    private Integer quantity;

    @NotNull
    @Column(name = DB.Field.BillItem.UOM)
    private Integer uom;

    @NotNull
    @Column(name = DB.Field.BillItem.PRICE)
    private Double price;

    @Column(name = DB.Field.BillItem.TAX1)
    private Double tax1;

    @Column(name = DB.Field.BillItem.TAX2)
    private Double tax2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUom() {
        return uom;
    }

    public void setUom(Integer uom) {
        this.uom = uom;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTax1() {
        return tax1;
    }

    public void setTax1(Double tax1) {
        this.tax1 = tax1;
    }

    public Double getTax2() {
        return tax2;
    }

    public void setTax2(Double tax2) {
        this.tax2 = tax2;
    }
}

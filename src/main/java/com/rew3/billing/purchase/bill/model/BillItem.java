package com.rew3.billing.purchase.bill.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = DB.Table.INVOICE_ITEM)
public class BillItem {


    @Id
    @JsonIgnore
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")

    @Column(name = DB.Field.InvoiceItem.ID, updatable = false)
    private String id;


    @JsonIgnore
    @JoinColumn(name = DB.Field.InvoiceItem.INVOICE_ID)
    @ManyToOne
    private Bill bill;


    @NotEmpty
    @Column(name = DB.Field.InvoiceItem.TITLE)
    private String title;

    @Column(name = DB.Field.InvoiceItem.DESCRIPTION)
    private String description;

    @NotNull
    @Column(name = DB.Field.InvoiceItem.QUANTITY)
    private Integer quantity;

    @NotNull
    @Column(name = DB.Field.InvoiceItem.PRICE)
    private Double price;

    @Column(name = DB.Field.InvoiceItem.TAX_TYPE)
    private String taxType;

    @Column(name = DB.Field.InvoiceItem.TAX)
    private Double tax;

    @Column(name = DB.Field.InvoiceItem.DISCOUNT_TYPE)
    private String discountType;

    @Column(name = DB.Field.InvoiceItem.DISCOUNT)
    private Double discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setTaxType(Flags.CalculationType taxType) {
        this.taxType = taxType.toString();
    }

    public String getTaxType() {
        return taxType;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Flags.CalculationType discountType) {
        this.discountType = discountType.toString();
    }


}

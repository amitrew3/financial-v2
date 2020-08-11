package com.rew3.billing.invoice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.product.model.Product;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.commission.transaction.model.RmsTransaction;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Parser;
import com.rew3.finance.accountingcode.model.AccountingCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = DB.Table.INVOICE_ITEM)
public class InvoiceItem {


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
    private Invoice invoice;


    @Column(name = DB.Field.InvoiceItem.TITLE)
    private String title;

    @Column(name = DB.Field.InvoiceItem.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.InvoiceItem.QUANTITY)
    private Integer quantity;


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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    public void setTaxType(Flags.TaxType taxType) {
        this.taxType = taxType.toString();
    }

    public String getTaxType() {
        return taxType;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Flags.InvoiceDiscountType discountType) {
        this.discountType = discountType.toString();
    }


}

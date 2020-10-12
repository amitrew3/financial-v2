package com.rew3.sale.recurringinvoice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.model.DB;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.salestax.model.SalesTax;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = DB.Table.INVOICE_ITEM)
public class RecurringInvoiceItem {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")

    @Column(name = DB.Field.InvoiceItem.ID, updatable = false)
    private String id;


    
    @JoinColumn(name = DB.Field.InvoiceItem.INVOICE_ID)
    @ManyToOne
    private RecurringInvoice recurringInvoice;

    @NotNull(message = "Product must not be null")
    @JoinColumn(name = DB.Field.InvoiceItem.PRODUCT_ID)
    @ManyToOne
    private Product product;

    @NotNull(message = "Quantity must not be null")
    @Column(name = DB.Field.InvoiceItem.QUANTITY)
    private Integer quantity;

    @NotNull
    @Column(name = DB.Field.InvoiceItem.UOM)
    private String uom;

    @NotNull(message = "Price must not be null")
    @Column(name = DB.Field.InvoiceItem.PRICE)
    private Double price;

    @OneToOne
    @JoinColumn(name = DB.Field.InvoiceItem.TAX1)
    private SalesTax tax1;

    @OneToOne
    @JoinColumn(name = DB.Field.InvoiceItem.TAX2)
    private SalesTax tax2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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


    public RecurringInvoice getRecurringInvoice() {
        return recurringInvoice;
    }

    public void setRecurringInvoice(RecurringInvoice recurringInvoice) {
        this.recurringInvoice = recurringInvoice;
    }
}

package com.rew3.billing.sale.invoice.model;


public class PaymentTermDTO extends AbstractDTO {


    private String name;

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



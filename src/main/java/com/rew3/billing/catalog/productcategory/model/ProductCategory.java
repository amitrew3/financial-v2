package com.rew3.billing.catalog.productcategory.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = DB.Table.PRODUCT_CATEGORY)
public class ProductCategory extends AbstractEntity {


    @Column(name = DB.Field.ProductCategory.TITLE)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = DB.Field.ProductCategory.DESCRIPTION)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

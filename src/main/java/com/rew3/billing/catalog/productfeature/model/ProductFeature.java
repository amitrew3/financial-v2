package com.rew3.billing.catalog.productfeature.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DB.Table.PRODUCT_FEATURE)
public class ProductFeature extends AbstractEntity implements Serializable {

    @Column(name = DB.Field.ProductFeature.TITLE)
    private String title;

    @Column(name = DB.Field.ProductFeature.DESCRIPTION)
    private String description;

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

}

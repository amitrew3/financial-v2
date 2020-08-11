package com.rew3.billing.catalog.product.model;


import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.PRODUCT_CATEGORY_LINK)
public class ProductCategoryLink extends AbstractEntity {


    @JoinColumn(name = DB.Field.ProductCategoryLink.PRODUCT_ID)
    @ManyToOne
    private Product product;

    @JoinColumn(name = DB.Field.ProductCategoryLink.PRODUCT_CATEGORY_ID)
    @ManyToOne
    private ProductCategory productCategory;

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}


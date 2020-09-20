package com.rew3.catalog.product.model;

import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

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
    @Column(name = DB.Field.Product.TAX1)
    private String tax1;
    @Column(name = DB.Field.Product.TAX2)
    private String tax2;


    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTax1() {
        return tax1;
    }

    public void setTax1(String tax1) {
        this.tax1 = tax1;
    }

    public String getTax2() {
        return tax2;
    }

    public void setTax2(String tax2) {
        this.tax2 = tax2;
    }

    public List<Map<String, Object>> getCategories() {
        List<Map<String, Object>> categories = null;
        if (this.get_id() != null) {
            categories = (List<Map<String, Object>>) HibernateUtils.selectSQL("SELECT pc.* FROM product_category pc "
                    + " INNER JOIN product_category_link pcl ON pc.id = pcl.product_category_id "
                    + " WHERE pcl.product_id = " + this.get_id());
        }

        return categories;
    }

    public List<Map<String, Object>> getFeatures() {
        List<Map<String, Object>> features = null;
        if (this.get_id() != null) {
            features = (List<Map<String, Object>>) HibernateUtils.selectSQL("SELECT pf.* FROM product_feature pf "
                    + " INNER JOIN product_feature_link pfl ON pf.id = pfl.product_feature_id "
                    + " WHERE pfl.product_id = " + this.get_id());
        }

        return features;
    }

    public List<Map<String, Object>> getRatePlans() {
        List<Map<String, Object>> ratePlans = null;
        if (this.get_id() != null) {
            ratePlans = (List<Map<String, Object>>) HibernateUtils.selectSQL("SELECT prp.* FROM product_rate_plan prp "
                    + " INNER JOIN product_rate_plan_link prpl ON prp.id = prpl.product_rate_plan_id "
                    + " WHERE prpl.product_id = " + this.get_id());
        }

        return ratePlans;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

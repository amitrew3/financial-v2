package com.rew3.billing.catalog.product.model;

import com.rew3.common.database.HibernateUtils;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.accounting.accountingcode.model.AccountingCode;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = DB.Table.PRODUCT)
public class Product extends AbstractEntity {


    @Column(name = DB.Field.Product.TITLE)
    private String title;

    @Column(name = DB.Field.Product.DESCRIPTION)
    private String description;

    @JoinColumn(name = DB.Field.Product.ACCOUNTING_CODE_ID)
    @ManyToOne
    private AccountingCode accountingCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


   /* public Long getAccountingCodeId() {
        return accountingCodeId;
    }

    public void setAccountingCodeId(Long accountingCodeId) {
        this.accountingCodeId = accountingCodeId;
    }*/

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

   /* public AccountingCode getAccountingCode() throws CommandException {
        AccountingCode aCode = null;
        if (accountingCodeId != null) {
            aCode = (AccountingCode) HibernateUtils.get(AccountingCode.class, accountingCodeId);
        }
        return aCode;
    }*/


    public AccountingCode getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(AccountingCode accountingCode) {
        this.accountingCode = accountingCode;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

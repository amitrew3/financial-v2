package com.rew3.billing.catalog.product.model;


import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;

@Entity
@Table(name= DB.Table.PRODUCT_RATE_PLAN_LINK)
public class ProductRatePlanLink extends AbstractEntity  {

	@JoinColumn(name = DB.Field.ProductRatePlanLink.PRODUCT_ID)
	@ManyToOne
	private Product product;

	@JoinColumn(name = DB.Field.ProductRatePlanLink.PRODUCT_RATE_PLAN_ID)
	@ManyToOne
	ProductRatePlan productRatePlan;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductRatePlan getProductRatePlan() {
		return productRatePlan;
	}

	public void setProductRatePlan(ProductRatePlan productRatePlan) {
		this.productRatePlan = productRatePlan;
	}
}

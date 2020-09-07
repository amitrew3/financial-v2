package com.rew3.billing.catalog.product.model;


import com.rew3.billing.catalog.productfeature.model.ProductFeature;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;

@Entity
@Table(name= DB.Table.PRODUCT_FEATURE_LINK)
public class ProductFeatureLink extends AbstractEntity {

	@JoinColumn(name = DB.Field.ProductFeatureLink.PRODUCT_ID)
	@ManyToOne
	private Product product;

	@JoinColumn(name="product_feature_id")
	@ManyToOne
	private ProductFeature productFeature;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductFeature getProductFeature() {
		return productFeature;
	}

	public void setProductFeature(ProductFeature productFeature) {
		this.productFeature = productFeature;
	}
}

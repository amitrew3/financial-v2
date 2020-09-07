package com.rew3.billing.catalog.productrateplan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.PRODUCT_RATE_PLAN_CHARGE)
public class ProductRatePlanCharge extends AbstractEntity {

	@JsonIgnore
	@JoinColumn(name = DB.Field.ProductRatePlanCharge.PRODUCT_RATE_PLAN_ID)
	@ManyToOne
	private ProductRatePlan productRatePlan;

	@Column(name =  DB.Field.ProductRatePlanCharge.TITLE)
	private String title;

	@Column(name =  DB.Field.ProductRatePlanCharge.DESCRIPTION)
	private String description;

	@Column(name =  DB.Field.ProductRatePlanCharge.AMOUNT)
	private Double amount;

	@Column(name =  DB.Field.ProductRatePlanCharge.UOM)
	private String uom;

	@Column(name =  DB.Field.ProductRatePlanCharge.BILLING_PERIOD)
	private Flags.TimePeriod billingPeriod;

	@Column(name =  DB.Field.ProductRatePlanCharge.DISCOUNT_TYPE)
	private Flags.RatePlanChargeDiscountType discountType;

	@Column(name =  DB.Field.ProductRatePlanCharge.DISCOUNT)
	private Double discount;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public ProductRatePlan getProductRatePlan() {
		return productRatePlan;
	}

	public void setProductRatePlan(ProductRatePlan productRatePlan) {
		this.productRatePlan = productRatePlan;
	}

	public Flags.RatePlanChargeDiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Flags.RatePlanChargeDiscountType discountType) {
		this.discountType = discountType;
	}

	public Flags.TimePeriod getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(Flags.TimePeriod billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
}

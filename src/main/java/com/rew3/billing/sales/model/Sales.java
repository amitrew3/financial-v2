package com.rew3.billing.sales.model;


import com.rew3.billing.catalog.product.model.Product;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.billing.normaluser.model.NormalUser;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.Flags;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="sales")
public class Sales extends AbstractEntity{
	
	@JoinColumn(name="product_id")
	@ManyToOne
	private Product product;

	@JoinColumn(name="product_rate_plan_id")
	@ManyToOne
	private ProductRatePlan productRatePlan;
	
	@JoinColumn(name="customer_id")
	@ManyToOne
	private NormalUser customer;

	@Column(name="start_date")
	private Timestamp startDate;
	
	@Column(name="end_date")
	private Timestamp endDate;

	@Column(name="is_invoiced")
	private boolean isInvoiced;
	
	@Column(name="next_invoice_at")
	private Timestamp nextInvoiceAt;

	/*public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductRatePlanId() {
		return productRatePlanId;
	}

	public void setProductRatePlanId(Long productRatePlanId) {
		this.productRatePlanId = productRatePlanId;
	}

*/
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getNextInvoiceAt() {
		return nextInvoiceAt;
	}

	public void setNextInvoiceAt(Timestamp nextInvoiceAt) {
		this.nextInvoiceAt = nextInvoiceAt;
	}


	public NormalUser getCustomer() {
		return customer;
	}

	public void setCustomer(NormalUser customer) {
		this.customer= customer;
	}


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

	public boolean isInvoiced() {
		return isInvoiced;
	}

	public void setInvoiced(boolean invoiced) {
		isInvoiced = invoiced;
	}
}

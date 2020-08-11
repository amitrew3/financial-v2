package com.rew3.billing.catalog.productrateplan.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.DB;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = DB.Table.PRODUCT_RATE_PLAN)
public class ProductRatePlan extends AbstractEntity{


	@Column(name = DB.Field.ProductRatePlan.TITLE)
	private String title;	

	@Column(name = DB.Field.ProductRatePlan.DESCRIPTION)
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

	public List<ProductRatePlanCharge> getCharges() {
		List<ProductRatePlanCharge> charges = null;
		if(super.get_id() != null) {
			charges = HibernateUtils.select("FROM ProductRatePlanCharge WHERE product_rate_plan_id = "+super.get_id());
		}
		
		return charges;
	}


}

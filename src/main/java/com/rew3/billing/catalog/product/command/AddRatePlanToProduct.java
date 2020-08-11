package com.rew3.billing.catalog.product.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class AddRatePlanToProduct extends Command implements ICommand {
	public AddRatePlanToProduct(HashMap<String, Object> data) {
		super(data);
	}
	
	public AddRatePlanToProduct(String productId, String rateplanId, Transaction trx) {
		super();
		this.data.put("productId", productId);
		this.data.put("rateplanId", rateplanId);
		this.trx = trx;
	}
	
	public AddRatePlanToProduct(String productId, String rateplanId) {
		this(productId, rateplanId, null);
	}
}
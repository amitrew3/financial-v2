package com.rew3.catalog.product.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class AddFeatureToProduct extends Command implements ICommand {
	public AddFeatureToProduct(HashMap<String, Object> data) {
		super(data);
	}
	
	public AddFeatureToProduct(String productId, String featureId, Transaction trx) {
		super();
		this.data.put("productId", productId);
		this.data.put("featureId", featureId);
		this.trx = trx;
	}
	
	public AddFeatureToProduct(String productId, String featureId) {
		this(productId, featureId, null);
	}
}
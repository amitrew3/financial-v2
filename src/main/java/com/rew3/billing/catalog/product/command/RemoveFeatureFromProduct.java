package com.rew3.billing.catalog.product.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class RemoveFeatureFromProduct extends Command implements ICommand {
	public RemoveFeatureFromProduct(HashMap<String, Object> data) {
		super(data);
	}
	
	public RemoveFeatureFromProduct(String productId, String featureId, Transaction Trx) {
		super();
		this.data.put("productId", productId);
		this.data.put("featureId", featureId);
		this.trx = trx;
	}

	
	public RemoveFeatureFromProduct(String productId, Transaction Trx) {
		super();
		this.data.put("productId", productId);
		this.trx = trx;
	}
	
	public RemoveFeatureFromProduct(String productId, String featureId) {
		this(productId, featureId, null);
	}
	
	public RemoveFeatureFromProduct(String productId) {
		this(productId, (Transaction) null);
	}
}
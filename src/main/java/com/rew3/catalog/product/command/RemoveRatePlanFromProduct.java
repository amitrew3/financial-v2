package com.rew3.catalog.product.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class RemoveRatePlanFromProduct extends Command implements ICommand {
	public RemoveRatePlanFromProduct(HashMap<String, Object> data) {
		super(data);
	}
	
	public RemoveRatePlanFromProduct(String productId, String rateplanId, Transaction Trx) {
		super();
		this.data.put("productId", productId);
		this.data.put("rateplanId", rateplanId);
		this.trx = trx;
	}
	
	public RemoveRatePlanFromProduct(String productId, Transaction Trx) {
		super();
		this.data.put("productId", productId);
		this.trx = trx;
	}
	
	public RemoveRatePlanFromProduct(String productId, String rateplanId) {
		this(productId, rateplanId, null);
	}
	
	public RemoveRatePlanFromProduct(String productId) {
		this(productId, (Transaction) null);
	}
}
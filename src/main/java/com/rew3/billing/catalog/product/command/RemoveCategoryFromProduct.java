package com.rew3.billing.catalog.product.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class RemoveCategoryFromProduct extends Command implements ICommand {
	public RemoveCategoryFromProduct(HashMap<String, Object> data) {
		super(data);
	}

	public RemoveCategoryFromProduct(String productId, String categoryId, Transaction trx) {
		super();
		this.data.put("productId", productId);
		this.data.put("categoryId", categoryId);
		this.trx = trx;
	}
	
	public RemoveCategoryFromProduct(String productId, Transaction trx) {
		super();
		this.data.put("productId", productId);
		this.trx = trx;
	}
	
	public RemoveCategoryFromProduct(String productId, String categoryId) {
		this(productId, categoryId, null);
	}
	
	public RemoveCategoryFromProduct(String productId) {
		this(productId, (Transaction) null);
	}
}
package com.rew3.billing.catalog.product.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class AddCategoryToProduct extends Command implements ICommand {
	public AddCategoryToProduct(HashMap<String, Object> data) {
		super(data);
	}
	
	public AddCategoryToProduct(String productId, String categoryId, Transaction trx) {
		super();
		this.data.put("productId", productId);
		this.data.put("categoryId", categoryId);
		this.trx = trx;
	}
	
	public AddCategoryToProduct(String productId, String categoryId) {
		this(productId, categoryId, null);
	}
}
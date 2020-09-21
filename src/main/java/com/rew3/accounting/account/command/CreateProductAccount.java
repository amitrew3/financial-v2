package com.rew3.accounting.account.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.catalog.product.model.Product;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags.AccountingCodeSegment;
import com.rew3.common.model.Flags.AccountingHead;
import com.rew3.common.model.Flags.EntityType;

public class CreateProductAccount extends Command implements ICommand {
	public CreateProductAccount(HashMap<String, Object> data) {
		this(data, null);
	}

	public CreateProductAccount(HashMap<String, Object> data, Transaction trx) {
		super(data, trx);
	}

	public CreateProductAccount(String productId, Transaction trx) {
		super(null, trx);
		this.set("productId", productId);
	}

	public CreateProductAccount(String productId) {
		super(null, null);
		this.set("productId", productId);
	}
	
	public CreateProductAccount(Product p, Transaction trx) {
		super(null, trx);
		this.set("product", p);
	}

	public CreateProductAccount(Product p) {
		super(null, null);
		this.set("product", p);
	}

	public CreateProductAccount(String userId, AccountingHead head, String entityId, EntityType entityType,
								AccountingCodeSegment segment, Transaction trx) {
		super(null, trx);
		this.set("userId", userId);
		this.set("head", head);
		this.set("entityId", entityId);
		this.set("entityType", entityType);
		this.set("segment", segment);
	}
}
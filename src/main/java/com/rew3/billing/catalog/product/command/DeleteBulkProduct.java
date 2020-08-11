package com.rew3.billing.catalog.product.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteBulkProduct extends Command implements ICommand {
	public DeleteBulkProduct(HashMap<String, Object> data) {
		super(data);
		
	}

}
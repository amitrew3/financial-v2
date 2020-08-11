package com.rew3.billing.catalog.productcategory.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteProductCategory extends Command implements ICommand {
	public DeleteProductCategory(HashMap<String, Object> data) {
		super(data);
		
	}
	public DeleteProductCategory(HashMap<String, Object> data,Transaction trx) {
		super(data,trx);

	}
}
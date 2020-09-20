package com.rew3.catalog.product.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteProduct extends Command implements ICommand {
	public DeleteProduct(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteProduct(HashMap<String, Object> data,Transaction trx) {
		super(data,trx);

	}
}
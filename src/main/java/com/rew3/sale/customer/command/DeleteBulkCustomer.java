package com.rew3.sale.customer.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkCustomer extends Command implements ICommand {
	public DeleteBulkCustomer(HashMap<String, Object> data) {
		super(data);
	}
}
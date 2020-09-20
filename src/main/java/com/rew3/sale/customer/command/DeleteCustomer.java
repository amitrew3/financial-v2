package com.rew3.sale.customer.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteCustomer extends Command implements ICommand {
	public DeleteCustomer(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteCustomer(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}
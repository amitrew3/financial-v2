package com.rew3.sale.receipt.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteReceipt extends Command implements ICommand {
	public DeleteReceipt(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteReceipt(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}
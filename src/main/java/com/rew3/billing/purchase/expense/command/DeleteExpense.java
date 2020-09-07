package com.rew3.billing.purchase.expense.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteExpense extends Command implements ICommand {
	public DeleteExpense(HashMap<String, Object> data) {
		super(data);
	}

	public DeleteExpense(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}

}
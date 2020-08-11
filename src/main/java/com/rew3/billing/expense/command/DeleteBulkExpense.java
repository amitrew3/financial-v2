package com.rew3.billing.expense.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkExpense extends Command implements ICommand {
	public DeleteBulkExpense(HashMap<String, Object> data) {
		super(data);
	}
}
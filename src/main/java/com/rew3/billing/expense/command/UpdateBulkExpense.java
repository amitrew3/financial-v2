package com.rew3.billing.expense.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkExpense extends Command implements ICommand {
	public UpdateBulkExpense(List<HashMap<String, Object>> data) {
		super(data);
	}

}
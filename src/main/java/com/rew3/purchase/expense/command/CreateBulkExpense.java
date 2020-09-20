package com.rew3.purchase.expense.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkExpense extends Command implements ICommand {
	public CreateBulkExpense(List<HashMap<String, Object>> data) {
		super(data);
	}
}
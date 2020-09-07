package com.rew3.billing.sale.estimate.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkTerm extends Command implements ICommand {
	public CreateBulkTerm(List<HashMap<String, Object>> data) {
		super(data);
	}
}
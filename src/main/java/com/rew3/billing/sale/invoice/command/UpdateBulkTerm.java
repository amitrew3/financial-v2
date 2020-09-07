package com.rew3.billing.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkTerm extends Command implements ICommand {
	public UpdateBulkTerm(List<HashMap<String, Object>> data) {
		super(data);
	}

}
package com.rew3.billing.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkInvoice extends Command implements ICommand {
	public UpdateBulkInvoice(List<HashMap<String, Object>> data) {
		super(data);
	}

}
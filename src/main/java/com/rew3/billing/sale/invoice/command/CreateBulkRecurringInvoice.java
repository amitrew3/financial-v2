package com.rew3.billing.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkRecurringInvoice extends Command implements ICommand {
	public CreateBulkRecurringInvoice(List<HashMap<String, Object>> data) {
		super(data);
	}
}
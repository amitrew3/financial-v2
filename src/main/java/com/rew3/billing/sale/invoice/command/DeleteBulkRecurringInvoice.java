package com.rew3.billing.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkRecurringInvoice extends Command implements ICommand {
	public DeleteBulkRecurringInvoice(HashMap<String, Object> data) {
		super(data);
	}
}
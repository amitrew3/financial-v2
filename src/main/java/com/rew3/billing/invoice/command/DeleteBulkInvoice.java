package com.rew3.billing.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkInvoice extends Command implements ICommand {
	public DeleteBulkInvoice(HashMap<String, Object> data) {
		super(data);
	}
}
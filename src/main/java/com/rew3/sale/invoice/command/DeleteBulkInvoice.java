package com.rew3.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class DeleteBulkInvoice extends Command implements ICommand {
	public DeleteBulkInvoice(List<HashMap<String, Object>> data) {
		super(data);
	}
}
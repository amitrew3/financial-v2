package com.rew3.billing.sale.invoice.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateInvoiceAttachment extends Command implements ICommand {
	public UpdateInvoiceAttachment(HashMap<String, Object> data) {
		super(data);
	}
}
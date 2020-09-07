package com.rew3.billing.sale.estimate.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateInvoiceAttachment extends Command implements ICommand {
	public UpdateInvoiceAttachment(HashMap<String, Object> data) {
		super(data);
	}
}
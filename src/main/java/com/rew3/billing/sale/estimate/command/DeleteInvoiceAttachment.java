package com.rew3.billing.sale.estimate.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeleteInvoiceAttachment extends Command implements ICommand {
	public DeleteInvoiceAttachment(String attachmentId) {
		super();
		this.set("id", attachmentId);
	}
}
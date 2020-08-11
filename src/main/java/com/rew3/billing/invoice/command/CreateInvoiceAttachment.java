package com.rew3.billing.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateInvoiceAttachment extends Command implements ICommand {
	public CreateInvoiceAttachment(String entityId, String filename) {
		super();
		this.set("entityId", entityId);
		this.set("filename", filename);
	}
}
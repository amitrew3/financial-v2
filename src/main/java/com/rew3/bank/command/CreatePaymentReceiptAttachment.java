package com.rew3.bank.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreatePaymentReceiptAttachment extends Command implements ICommand {
	public CreatePaymentReceiptAttachment(String entityId, String filename) {
		super();
		this.set("entityId", entityId);
		this.set("filename", filename);
	}
}
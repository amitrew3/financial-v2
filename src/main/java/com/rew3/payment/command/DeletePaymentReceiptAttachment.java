package com.rew3.payment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeletePaymentReceiptAttachment extends Command implements ICommand {
	public DeletePaymentReceiptAttachment(String attachmentId) {
		super();
		this.set("id", attachmentId);
	}
}
package com.rew3.payment.recurringinvoicepayment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeleteRecurringInvoicePayment extends Command implements ICommand {
	public String id;

	public DeleteRecurringInvoicePayment(String id) {
		this.id = id;
	}
}
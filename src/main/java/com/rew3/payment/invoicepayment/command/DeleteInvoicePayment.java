package com.rew3.payment.invoicepayment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteInvoicePayment extends Command implements ICommand {
	public String id;

	public DeleteInvoicePayment(String id) {
		this.id = id;
	}
}
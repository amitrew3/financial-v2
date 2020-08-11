package com.rew3.billing.invoice.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateRecurringInvoice extends Command implements ICommand {
	public CreateRecurringInvoice(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/recurring-invoice/create";
		boolean valid = this.validate();
		if (!valid) {
			throw new CommandException("invalid");
		}
	}
	

	

}
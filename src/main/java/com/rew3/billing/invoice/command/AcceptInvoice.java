package com.rew3.billing.invoice.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class AcceptInvoice extends Command implements ICommand {
	public AcceptInvoice(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema ="billing/invoice/accept_internal";
		this.validate();
	}
}
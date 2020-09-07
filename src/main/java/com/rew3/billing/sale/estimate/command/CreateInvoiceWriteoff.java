package com.rew3.billing.sale.estimate.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateInvoiceWriteoff extends Command implements ICommand {
	public CreateInvoiceWriteoff(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "invoice/writeoff";
		this.validate();		
	}
}
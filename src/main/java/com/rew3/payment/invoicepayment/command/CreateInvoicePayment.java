package com.rew3.payment.invoicepayment.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateInvoicePayment extends Command implements ICommand {
	public CreateInvoicePayment(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/paymentoption/create";
		this.validate();
	}
}
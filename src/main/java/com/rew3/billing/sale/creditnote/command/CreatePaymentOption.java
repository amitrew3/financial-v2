package com.rew3.billing.sale.creditnote.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreatePaymentOption extends Command implements ICommand {
	public CreatePaymentOption(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/paymentoption/create";
		this.validate();
	}
}
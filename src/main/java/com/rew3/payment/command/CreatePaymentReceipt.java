package com.rew3.payment.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreatePaymentReceipt extends Command implements ICommand {
	public CreatePaymentReceipt(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/payment/receipt/create";
		this.validate();
	}
}
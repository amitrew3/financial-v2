package com.rew3.bank.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateTrustTransaction extends Command implements ICommand {
	public CreateTrustTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/payment/transaction/trust/create";
		this.validate();
	}
}
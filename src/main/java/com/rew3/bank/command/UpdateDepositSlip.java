package com.rew3.bank.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateDepositSlip extends Command implements ICommand {
	public UpdateDepositSlip(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/payment/account/create";
		this.validate();
	}
}
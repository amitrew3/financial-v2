package com.rew3.billing.paymentoption.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeletePaymentOption extends Command implements ICommand {
	public DeletePaymentOption(HashMap<String, Object> data) {
		super(data);
		//this.validate();
	}
}
package com.rew3.billing.salesv1.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateSales extends Command implements ICommand {
	public CreateSales(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/sales/create";
		this.validate();
	}
}
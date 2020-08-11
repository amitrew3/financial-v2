package com.rew3.commission.deduction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateDeduction extends Command implements ICommand {
	public CreateDeduction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/deduction/create";
		this.validate();
	}
}
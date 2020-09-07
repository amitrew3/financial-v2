package com.rew3.brokerage.deduction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateDeduction extends Command implements ICommand {
	public UpdateDeduction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/deduction/update";
		this.validate();
	}
}
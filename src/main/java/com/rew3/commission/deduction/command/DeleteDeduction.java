package com.rew3.commission.deduction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteDeduction extends Command implements ICommand {
	public DeleteDeduction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "sync/deduction/save";
		this.validate();
	}
}
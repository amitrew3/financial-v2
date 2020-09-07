package com.rew3.common.shared.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateAddress extends Command implements ICommand {
	public UpdateAddress(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/terms/update";
		this.validate();
	}
}
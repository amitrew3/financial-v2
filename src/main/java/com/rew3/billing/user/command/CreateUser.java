package com.rew3.billing.user.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.ICommand;

public class CreateUser extends CreateOrUpdateUserAbstract implements ICommand {
	public CreateUser(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/user/create";
		this.validate();
	}
}
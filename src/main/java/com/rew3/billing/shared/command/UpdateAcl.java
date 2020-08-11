package com.rew3.billing.shared.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateAcl extends Command implements ICommand {
	public UpdateAcl(HashMap<String, Object> data) {
		super(data);
	}
}
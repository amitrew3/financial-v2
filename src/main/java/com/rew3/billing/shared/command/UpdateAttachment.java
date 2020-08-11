package com.rew3.billing.shared.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateAttachment extends Command implements ICommand {
	public UpdateAttachment(HashMap<String, Object> data) {
		super(data);
	}
}
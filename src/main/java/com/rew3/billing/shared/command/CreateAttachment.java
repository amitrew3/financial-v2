package com.rew3.billing.shared.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateAttachment extends Command implements ICommand {
	public CreateAttachment(String entityId, String filename) {
		super();
		this.set("entityId", entityId);
		this.set("filename", filename);
	}
}
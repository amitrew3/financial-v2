package com.rew3.common.shared.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeleteAttachment extends Command implements ICommand {
	public DeleteAttachment(String attachmentId) {
		super();
		this.set("id", attachmentId);
	}
}
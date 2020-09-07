package com.rew3.brokerage.acp.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkAcp extends Command implements ICommand {
	public DeleteBulkAcp(HashMap<String, Object> data) {
		super(data);
	}
}
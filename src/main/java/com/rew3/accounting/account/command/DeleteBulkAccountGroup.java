package com.rew3.accounting.account.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkAccountGroup extends Command implements ICommand {
	public DeleteBulkAccountGroup(HashMap<String, Object> data) {
		super(data);
	}
}
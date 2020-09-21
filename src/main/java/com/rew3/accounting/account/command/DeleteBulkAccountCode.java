package com.rew3.accounting.account.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkAccountCode extends Command implements ICommand {
	public DeleteBulkAccountCode(HashMap<String, Object> data) {
		super(data);
	}
}
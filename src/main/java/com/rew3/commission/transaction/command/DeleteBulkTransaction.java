package com.rew3.commission.transaction.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkTransaction extends Command implements ICommand {
	public DeleteBulkTransaction(HashMap<String, Object> data) {
		super(data);
	}
}
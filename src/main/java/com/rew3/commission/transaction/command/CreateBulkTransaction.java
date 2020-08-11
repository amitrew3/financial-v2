package com.rew3.commission.transaction.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkTransaction extends Command implements ICommand {
	public CreateBulkTransaction(List<HashMap<String, Object>> data) {
		super(data);
	}
}
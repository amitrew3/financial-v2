package com.rew3.commission.transaction.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkTransaction extends Command implements ICommand {
	public UpdateBulkTransaction(List<HashMap<String, Object>> data) {
		super(data);
	}

}
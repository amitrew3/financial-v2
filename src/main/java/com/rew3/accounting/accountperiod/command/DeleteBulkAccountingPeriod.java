package com.rew3.accounting.accountperiod.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkAccountingPeriod extends Command implements ICommand {
	public DeleteBulkAccountingPeriod(HashMap<String, Object> data) {
		super(data);
	}
}
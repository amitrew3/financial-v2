package com.rew3.accounting.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkAccountingClass extends Command implements ICommand {
	public DeleteBulkAccountingClass(HashMap<String, Object> data) {
		super(data);
	}
}
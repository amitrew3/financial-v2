package com.rew3.accounting.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkAccountingCode extends Command implements ICommand {
	public DeleteBulkAccountingCode(HashMap<String, Object> data) {
		super(data);
	}
}
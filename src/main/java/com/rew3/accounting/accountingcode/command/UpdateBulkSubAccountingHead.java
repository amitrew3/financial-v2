package com.rew3.accounting.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateBulkSubAccountingHead extends Command implements ICommand {
	public UpdateBulkSubAccountingHead(HashMap<String, Object> data) {
		super(data);
	}
}
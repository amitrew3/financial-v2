package com.rew3.accounting.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkSubAccountingHead extends Command implements ICommand {
	public CreateBulkSubAccountingHead(List<HashMap<String, Object>> data) {
		super(data);
	}
}
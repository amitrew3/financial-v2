package com.rew3.accounting.account.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkAccountGroup extends Command implements ICommand {
	public CreateBulkAccountGroup(List<HashMap<String, Object>> data) {
		super(data);
	}
}
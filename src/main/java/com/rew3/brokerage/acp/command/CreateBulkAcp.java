package com.rew3.brokerage.acp.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkAcp extends Command implements ICommand {
	public CreateBulkAcp(List<HashMap<String, Object>> data) {
		super(data);
	}
}
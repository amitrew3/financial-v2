package com.rew3.commission.acp.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkAcp extends Command implements ICommand {
	public UpdateBulkAcp(List<HashMap<String, Object>> data) {
		super(data);
	}
}
package com.rew3.sale.estimate.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateBulkEstimate extends Command implements ICommand {
	public CreateBulkEstimate(HashMap<String, Object> data) {
		super(data);
	}
}
package com.rew3.billing.catalog.productrateplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkProductRatePlan extends Command implements ICommand {
	public DeleteBulkProductRatePlan(HashMap<String, Object> data) {
		super(data);
	}
}
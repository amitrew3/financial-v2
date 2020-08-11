package com.rew3.billing.catalog.productfeature.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkProductFeature extends Command implements ICommand {
	public DeleteBulkProductFeature(HashMap<String, Object> data) {
		super(data);
	}
}
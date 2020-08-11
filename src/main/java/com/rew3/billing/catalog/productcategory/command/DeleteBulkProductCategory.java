package com.rew3.billing.catalog.productcategory.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkProductCategory extends Command implements ICommand {
	public DeleteBulkProductCategory(HashMap<String, Object> data) {
		super(data);
	}
}
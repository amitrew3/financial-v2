package com.rew3.purchase.vendor.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkVendor extends Command implements ICommand {
	public DeleteBulkVendor(HashMap<String, Object> data) {
		super(data);
	}
}
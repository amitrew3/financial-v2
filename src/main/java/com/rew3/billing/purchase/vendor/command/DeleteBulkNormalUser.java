package com.rew3.billing.purchase.vendor.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkNormalUser extends Command implements ICommand {
	public DeleteBulkNormalUser(HashMap<String, Object> data) {
		super(data);
	}
}
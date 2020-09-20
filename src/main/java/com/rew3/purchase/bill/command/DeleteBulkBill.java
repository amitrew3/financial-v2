package com.rew3.purchase.bill.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkBill extends Command implements ICommand {
	public DeleteBulkBill(HashMap<String, Object> data) {
		super(data);
	}
}
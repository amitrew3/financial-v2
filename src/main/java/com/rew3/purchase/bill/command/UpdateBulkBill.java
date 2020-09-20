package com.rew3.purchase.bill.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkBill extends Command implements ICommand {
	public UpdateBulkBill(List<HashMap<String, Object>> data) {
		super(data);
	}
}
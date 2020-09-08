package com.rew3.billing.purchase.bill.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateBulkBill extends Command implements ICommand {
	public CreateBulkBill(HashMap<String, Object> data) {
		super(data);
	}
}
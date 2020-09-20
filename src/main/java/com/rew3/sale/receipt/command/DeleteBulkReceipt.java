package com.rew3.sale.receipt.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkReceipt extends Command implements ICommand {
	public DeleteBulkReceipt(HashMap<String, Object> data) {
		super(data);
	}
}
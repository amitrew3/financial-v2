package com.rew3.purchase.vendor.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteVendor extends Command implements ICommand {
	public DeleteVendor(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteVendor(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}
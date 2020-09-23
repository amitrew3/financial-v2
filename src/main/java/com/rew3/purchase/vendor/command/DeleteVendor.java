package com.rew3.purchase.vendor.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteVendor extends Command implements ICommand {
	String id;

	public DeleteVendor(String id) {
		this.id = id;
	}
}
package com.rew3.billing.purchase.bill.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class AcceptBill extends Command implements ICommand {
	public AcceptBill(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema ="billing/invoice/accept_internal";
		this.validate();
	}
}
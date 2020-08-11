package com.rew3.billing.invoice.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateVendorBill extends Command implements ICommand {
	public CreateVendorBill(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/invoice/create_external";
		this.validate();
	}

	public CreateVendorBill(HashMap<String, Object> data, Transaction trx) throws CommandException {
		this(data);
		this.trx = trx;
	}
}
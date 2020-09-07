package com.rew3.billing.sale.estimate.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

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
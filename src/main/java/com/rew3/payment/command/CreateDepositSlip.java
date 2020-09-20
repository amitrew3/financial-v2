package com.rew3.payment.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateDepositSlip extends Command implements ICommand {
	public CreateDepositSlip(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/payment/transaction/deposit_slip/create";
		this.validate();
	}

	public CreateDepositSlip(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}
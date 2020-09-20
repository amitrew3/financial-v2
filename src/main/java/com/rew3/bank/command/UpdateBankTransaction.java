package com.rew3.bank.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateBankTransaction extends Command implements ICommand {
	public UpdateBankTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema ="billing/payment/transaction/update";
		this.validate();
	}

	public UpdateBankTransaction(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}
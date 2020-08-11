package com.rew3.billing.payment.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.HashMap;

public class CreateBankTransaction extends Command implements ICommand {
	public CreateBankTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/payment/transaction/create";
		this.validate();

	}

	public CreateBankTransaction(HashMap<String, Object> data,String billingAccountId,Transaction trx) {
		super(data,trx);
		this.set("billingAccountId",billingAccountId);
	}

}
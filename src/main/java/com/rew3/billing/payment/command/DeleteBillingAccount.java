package com.rew3.billing.payment.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteBillingAccount extends Command implements ICommand {
	public DeleteBillingAccount(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteBillingAccount(HashMap<String, Object> data,Transaction trx) {
		super(data,trx);
	}
}
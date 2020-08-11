package com.rew3.billing.payment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBankTransaction extends Command implements ICommand {
	public DeleteBankTransaction(HashMap<String, Object> data) {
		super(data);
	}
}
package com.rew3.bank.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeletePaymentReceipt extends Command implements ICommand {
	public DeletePaymentReceipt(HashMap<String, Object> data) {
		super(data);
	}
}
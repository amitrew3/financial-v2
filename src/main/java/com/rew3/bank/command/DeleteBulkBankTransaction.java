package com.rew3.bank.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkBankTransaction extends Command implements ICommand {
	public DeleteBulkBankTransaction(HashMap<String, Object> data) {
		super(data);
	}
}
package com.rew3.accounting.transaction.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateTransaction extends Command implements ICommand {
	public UpdateTransaction(HashMap<String, Object> data) {
		
	}
}
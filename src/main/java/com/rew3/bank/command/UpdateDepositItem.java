package com.rew3.bank.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateDepositItem extends Command implements ICommand {
	public UpdateDepositItem(HashMap<String, Object> data) {
		super(data);
	}
}
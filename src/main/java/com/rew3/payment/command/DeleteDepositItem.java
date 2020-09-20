package com.rew3.payment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteDepositItem extends Command implements ICommand {
	public DeleteDepositItem(HashMap<String, Object> data) {
		super(data);
	}
}
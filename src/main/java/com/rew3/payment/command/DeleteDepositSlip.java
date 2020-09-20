package com.rew3.payment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteDepositSlip extends Command implements ICommand {
	public DeleteDepositSlip(HashMap<String, Object> data) {
		super(data);
	}
}
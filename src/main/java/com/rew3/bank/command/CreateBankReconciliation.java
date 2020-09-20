package com.rew3.bank.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateBankReconciliation extends Command implements ICommand {
	public CreateBankReconciliation(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/payment/transaction/reconciliation/create";
		this.validate();
	}
}
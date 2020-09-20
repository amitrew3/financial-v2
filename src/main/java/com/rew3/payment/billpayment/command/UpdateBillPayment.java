package com.rew3.payment.billpayment.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateBillPayment extends Command implements ICommand {
	public UpdateBillPayment(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/paymentoption/update";
		this.validate();
	}
}
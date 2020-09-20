package com.rew3.payment.billpayment.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateBillPayment extends Command implements ICommand {
	public CreateBillPayment(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/paymentoption/create";
		this.validate();
	}
}
package com.rew3.payment.billpayment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBillPayment extends Command implements ICommand {
	public DeleteBillPayment(HashMap<String, Object> data) {
		super(data);
		//this.validate();
	}
}
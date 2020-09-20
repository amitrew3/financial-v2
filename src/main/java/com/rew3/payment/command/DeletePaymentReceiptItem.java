package com.rew3.payment.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeletePaymentReceiptItem extends Command implements ICommand {
	public DeletePaymentReceiptItem(HashMap<String, Object> data) throws CommandException {
		super(data);
		//this.validationSchema ="payment/receipt/send";
//		this.validationSchema ="payment/receipt/receive";
		this.validate();
	}
}
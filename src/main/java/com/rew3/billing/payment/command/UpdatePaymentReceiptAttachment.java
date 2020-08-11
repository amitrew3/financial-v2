package com.rew3.billing.payment.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdatePaymentReceiptAttachment extends Command implements ICommand {
	public UpdatePaymentReceiptAttachment(HashMap<String, Object> data) {
		super(data);
	}
}
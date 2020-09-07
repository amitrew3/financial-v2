package com.rew3.billing.sale.estimate.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateInvoiceRefundRequest extends Command implements ICommand {
	public CreateInvoiceRefundRequest(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/invoice/refund_request";
		this.validate();
	}
}
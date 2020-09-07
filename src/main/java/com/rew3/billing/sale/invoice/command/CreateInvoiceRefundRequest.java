package com.rew3.billing.sale.invoice.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateInvoiceRefundRequest extends Command implements ICommand {
	public CreateInvoiceRefundRequest(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/invoice/refund_request";
		this.validate();
	}
}
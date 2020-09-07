package com.rew3.billing.sale.invoice.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateInvoiceRefundResponse extends Command implements ICommand {
	public CreateInvoiceRefundResponse(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/invoice/refund_approve";
		this.validate();
	}
}
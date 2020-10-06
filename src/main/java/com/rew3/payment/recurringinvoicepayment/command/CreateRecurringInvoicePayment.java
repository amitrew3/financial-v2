package com.rew3.payment.recurringinvoicepayment.command;

import com.avenue.financial.services.grpc.proto.invoicepayment.AddInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.AddRecurringInvoiceProto;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.AddRecurringInvoicePaymentProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateRecurringInvoicePayment extends Command implements ICommand {
	public AddRecurringInvoicePaymentProto addRecurringInvoicePaymentProto;

	public CreateRecurringInvoicePayment(AddRecurringInvoicePaymentProto addRecurringInvoicePaymentProto) {
		this.addRecurringInvoicePaymentProto = addRecurringInvoicePaymentProto;
	}
}
package com.rew3.payment.invoicepayment.command;

import com.avenue.financial.services.grpc.proto.invoicepayment.AddInvoicePaymentProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateInvoicePayment extends Command implements ICommand {
	public AddInvoicePaymentProto addInvoicePaymentProto;

	public CreateInvoicePayment(AddInvoicePaymentProto addInvoicePaymentProto) {
		this.addInvoicePaymentProto = addInvoicePaymentProto;
	}
}
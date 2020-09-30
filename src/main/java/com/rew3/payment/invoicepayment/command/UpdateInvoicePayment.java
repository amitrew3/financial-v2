package com.rew3.payment.invoicepayment.command;

import com.avenue.financial.services.grpc.proto.invoicepayment.UpdateInvoicePaymentProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateInvoicePayment extends Command implements ICommand {
	public UpdateInvoicePaymentProto updateInvoicePaymentProto;

	public UpdateInvoicePayment(UpdateInvoicePaymentProto updateInvoicePaymentProto) {
		this.updateInvoicePaymentProto = updateInvoicePaymentProto;
	}
}
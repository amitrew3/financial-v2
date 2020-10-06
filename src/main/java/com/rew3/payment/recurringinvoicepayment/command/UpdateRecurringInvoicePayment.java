package com.rew3.payment.recurringinvoicepayment.command;

import com.avenue.financial.services.grpc.proto.invoicepayment.UpdateInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.UpdateRecurringInvoicePaymentProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateRecurringInvoicePayment extends Command implements ICommand {
	public UpdateRecurringInvoicePaymentProto updateRecurringInvoicePaymentProto;

	public UpdateRecurringInvoicePayment(UpdateRecurringInvoicePaymentProto updateRecurringInvoicePaymentProto) {
		this.updateRecurringInvoicePaymentProto = updateRecurringInvoicePaymentProto;
	}
}
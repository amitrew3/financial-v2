package com.rew3.payment.billpayment.command;

import com.avenue.financial.services.grpc.proto.billpayment.UpdateBillPaymentProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateBillPayment extends Command implements ICommand {
	public UpdateBillPaymentProto updateBillPaymentProto;

	public UpdateBillPayment(UpdateBillPaymentProto updateBillPaymentProto) {
		this.updateBillPaymentProto = updateBillPaymentProto;
	}
}
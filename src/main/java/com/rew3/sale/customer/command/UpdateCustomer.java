package com.rew3.sale.customer.command;

import com.avenue.financial.services.grpc.proto.customer.UpdateCustomerProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateCustomer extends Command implements ICommand {
	UpdateCustomerProto updateCustomerProto;

	public UpdateCustomer(UpdateCustomerProto updateCustomerProto) {
		this.updateCustomerProto = updateCustomerProto;
	}
}
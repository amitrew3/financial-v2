package com.rew3.sale.customer.command;

import com.avenue.financial.services.grpc.proto.customer.AddCustomerProto;
import com.avenue.financial.services.grpc.proto.customer.CustomerProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateCustomer extends Command implements ICommand {
	AddCustomerProto addCustomerProto;

	public CreateCustomer(AddCustomerProto addCustomerProto) {
		this.addCustomerProto = addCustomerProto;
	}
}
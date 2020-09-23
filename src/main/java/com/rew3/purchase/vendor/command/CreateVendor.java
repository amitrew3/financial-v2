package com.rew3.purchase.vendor.command;

import com.avenue.financial.services.grpc.proto.vendor.AddVendorProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateVendor extends Command implements ICommand {
	AddVendorProto addVendorProto;

	public CreateVendor(AddVendorProto addVendorProto) {
		this.addVendorProto = addVendorProto;
	}
}
package com.rew3.purchase.vendor.command;

import com.avenue.financial.services.grpc.proto.vendor.UpdateVendorProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateVendor extends Command implements ICommand {
	UpdateVendorProto updateVendorProto;

	public UpdateVendor(UpdateVendorProto updateVendorProto) {
		this.updateVendorProto = updateVendorProto;
	}
}
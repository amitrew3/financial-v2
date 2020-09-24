package com.rew3.catalog.product.command;

import java.util.HashMap;

import com.avenue.financial.services.grpc.proto.product.UpdateProductProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class UpdateProduct extends Command implements ICommand {
	public UpdateProductProto updateProductProto;

	public UpdateProduct(UpdateProductProto updateProductProto) {
		this.updateProductProto = updateProductProto;
	}
}
package com.rew3.brokerage.acp.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateTieredAcp extends Command implements ICommand {
	public UpdateTieredAcp(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/acp/tieredacp/tieredcommissionstage/update";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
	public UpdateTieredAcp(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "commission/acp/tieredacp/tieredcommissionstage/update";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
}
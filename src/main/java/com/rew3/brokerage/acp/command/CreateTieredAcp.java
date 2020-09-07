package com.rew3.brokerage.acp.command;

import com.rew3.brokerage.acp.model.Acp;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateTieredAcp extends Command implements ICommand {
	public CreateTieredAcp(HashMap<String, Object> data) throws CommandException {
		super(data);
		/*this.validationSchema = "commission/acp/tieredacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("Not valid");
		}*/
	}
	public CreateTieredAcp(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		/*this.validationSchema = "commission/acp/tieredacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("Not valid");
		}*/
	}



	public CreateTieredAcp(HashMap<String, Object> data, Acp acp, Flags.SideOption sideOption, Transaction trx) {
		super(data,trx);

	}
}
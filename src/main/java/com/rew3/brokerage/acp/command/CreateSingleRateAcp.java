package com.rew3.brokerage.acp.command;

import com.rew3.brokerage.acp.model.Acp;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateSingleRateAcp extends Command implements ICommand {
    public CreateSingleRateAcp(HashMap<String, Object> data) throws CommandException {
        super(data);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

    public CreateSingleRateAcp(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

    public CreateSingleRateAcp(HashMap<String, Object> data, Acp acp, Flags.SideOption sideOption, Transaction trx) throws CommandException {
        super(data, trx);
        data.put("acp", acp);
        data.put("side", sideOption.toString());
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }
}
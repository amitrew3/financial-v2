package com.rew3.brokerage.acp.command;

import com.rew3.brokerage.acp.model.Acp;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateUpdateTieredAcp extends Command implements ICommand {
    public CreateUpdateTieredAcp(HashMap<String, Object> data) throws CommandException {
        super(data);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

    public CreateUpdateTieredAcp(HashMap<String, Object> data, Acp acp) throws CommandException {
        super((HashMap<String, Object>) data.get("tieredAcp"));
        this.data.put("acp",acp);
        this.validationSchema = "commission/acp/singlerateacp/create";
		/*boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

}
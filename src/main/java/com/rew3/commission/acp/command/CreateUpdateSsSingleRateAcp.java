package com.rew3.commission.acp.command;

import com.rew3.commission.acp.model.Acp;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateUpdateSsSingleRateAcp extends Command implements ICommand {
    public CreateUpdateSsSingleRateAcp(HashMap<String, Object> data) throws CommandException {
        super(data);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

    public CreateUpdateSsSingleRateAcp(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

    public CreateUpdateSsSingleRateAcp(HashMap<String, Object> data, Acp acp) throws Exception {
        super((HashMap<String, Object>) data.get("ss"));
        this.data.put("acp", acp);
        this.data.put("side", Flags.SideOption.SS.toString());

//        if(acp!=null){
//            DeleteSingleRateAcp deleteSingleRateAcp=new DeleteSingleRateAcp(this.data,acp,trx);
//            CommandRegister.getInstance().process(deleteSingleRateAcp);
//        }


		this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}

    }

    public CreateUpdateSsSingleRateAcp(HashMap<String, Object> data, Acp acp, Flags.SideOption sideOption, Transaction trx) throws CommandException {
        super(data, trx);
        data.put("acp", acp);
        data.put("side", Flags.SideOption.SS.toString());
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }
}
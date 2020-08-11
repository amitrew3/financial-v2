package com.rew3.commission.acp.command;

import com.rew3.commission.acp.model.Acp;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;

public class CreateUpdateLsSingleRateAcp extends Command implements ICommand {
    public CreateUpdateLsSingleRateAcp(HashMap<String, Object> data) throws CommandException {
        super(data);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

    public CreateUpdateLsSingleRateAcp(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }

    public CreateUpdateLsSingleRateAcp(HashMap<String, Object> data, Acp acp) throws Exception {
        super((HashMap<String, Object>) data.get("ls"));
        this.data.put("acp", acp);

        this.data.put("side", Flags.SideOption.LS.toString());


      /*  if (acp != null) {

            HashMap<String, Object> map = new HashMap<>();
            map.put("side", Flags.SideOption.LS.toString());
            DeleteSingleRateAcp deleteSingleRateAcp = new DeleteSingleRateAcp(map, acp, trx);
            CommandRegister.getInstance().process(deleteSingleRateAcp);
        }*/
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/

        this.validationSchema = "commission/acp/singlerateacp/create";
        boolean valid=this.validate();
        if(!valid){
            throw new CommandException("unable");
        }


    }


    public CreateUpdateLsSingleRateAcp(HashMap<String, Object> data, Acp acp, Flags.SideOption sideOption, Transaction trx) throws CommandException {
        super(data, trx);
        data.put("acp", acp);

		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
    }
}
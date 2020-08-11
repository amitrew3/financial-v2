package com.rew3.commission.acp.command;

import com.rew3.commission.acp.model.TieredAcp;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateUpdateSsTieredStage extends Command implements ICommand {
	public CreateUpdateSsTieredStage(HashMap<String, Object> data) throws CommandException {
		super(data);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
	}
	public CreateUpdateSsTieredStage(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data);
		this.validationSchema = "commission/acp/singlerateacp/create";
		/*boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
	}
	public CreateUpdateSsTieredStage(HashMap<String, Object> data, TieredAcp tieredAcp) throws Exception {
		super(data);
		this.data.put("tieredAcp", tieredAcp);
		this.data.put("side", Flags.SideOption.SS.toString());

//		DeleteTieredStage deleteTieredStage=new DeleteTieredStage(this.data,trx);
//		CommandRegister.getInstance().process(deleteTieredStage);
	}
}
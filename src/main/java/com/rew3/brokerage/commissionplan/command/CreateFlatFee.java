package com.rew3.brokerage.commissionplan.command;

import com.rew3.brokerage.commissionplan.model.CommissionPlan;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateFlatFee extends Command implements ICommand {
    public CreateFlatFee(HashMap<String, Object> data, CommissionPlan plan) throws CommandException, NotFoundException {
        super(data);
        this.data.put("commissionPlan", plan);


    }


    public CreateFlatFee(HashMap<String, Object> data, Transaction trx) throws CommandException, NotFoundException {
        super(data, trx);
        this.validationSchema = "commission/acp/singlerateacp/create";
		/*boolean valid=this.validate();
		if(!valid){
			throw new CommandException("    unable");
		}*/


    }

    public void validateFields(HashMap<String, Object> data) throws CommandException {


        if (data.get("side").toString().equals("BOTH") && (data.get("ss") == null | data.get("ls") == null)) {

            throw new CommandException("ls and ss, both required");
        }

        if (data.get("side").toString().equals("LS") && data.get("ls") == null) {

            throw new CommandException("ls  required");
        }
        if (data.get("side").toString().equals("SS") && data.get("ss") == null) {

            throw new CommandException("ss  required");
        }


    }


}
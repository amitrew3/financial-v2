package com.rew3.brokerage.commissionplan.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateCommissionPlan extends Command implements ICommand {
    public CreateCommissionPlan(HashMap<String, Object> data) throws CommandException, NotFoundException {
        super(data);


        Flags.CommissionPlanType side = Flags.CommissionPlanType.valueOf(data.get("type").toString());
        if (side == Flags.CommissionPlanType.FLAT_FEE) {
            this.validationSchema = "commission/commission-plan/create-flat";
        } else if (side == Flags.CommissionPlanType.SLIDING_SCALE) {
            this.validationSchema = "commission/commission-plan/create-sliding";
        }


        boolean valid = this.validate();
        // this.validateFields(data);
        if (!valid) {
            throw new CommandException("not valid");
        }

    }

    public CreateCommissionPlan(HashMap<String, Object> data, Transaction trx) throws CommandException, NotFoundException {
        super(data, trx);
        this.validationSchema = "commission/acp/singlerateacp/create";
		/*boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
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
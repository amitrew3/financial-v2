package com.rew3.brokerage.acp.command;

import com.rew3.brokerage.acp.AcpQueryHandler;
import com.rew3.brokerage.acp.model.Acp;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateUpdateAcp extends Command implements ICommand {
    public CreateUpdateAcp(HashMap<String, Object> data, String method) throws CommandException, NotFoundException {
        super(data);

        Flags.AcpType side = Flags.AcpType.valueOf(data.get("type").toString());
        if (side == Flags.AcpType.SINGLE_TIERED && method.equals("POST")) {
            this.validationSchema = "commission/acp/singlerateacp/create";
        } else if (side == Flags.AcpType.MULTI_TIERED && method.equals("POST")) {
            this.validationSchema = "commission/acp/tieredacp/create";
        } else if (side == Flags.AcpType.SINGLE_TIERED && method.equals("PUT")) {
            this.validationSchema = "commission/acp/singlerateacp/update";
        } else if (side == Flags.AcpType.MULTI_TIERED && method.equals("PUT")) {
            this.validationSchema = "commission/acp/tieredacp/update";
        }


        boolean valid = this.validate();
        this.validateFields(data);
        if (!valid) {
            throw new CommandException("not valid");
        }
        if (data.get("id") != null) {
            String id = data.get("id").toString();
            Acp acp = (Acp) new AcpQueryHandler().getById(id);
            this.data.put("acp", acp);

        }


    }

    public CreateUpdateAcp(HashMap<String, Object> data, Transaction trx) throws CommandException, NotFoundException {
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
package com.rew3.commission.acp.command;

import com.rew3.commission.acp.model.Acp;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteSingleRateAcp extends Command implements ICommand {
    public DeleteSingleRateAcp(HashMap<String, Object> data) {
        super(data);
    }

    public DeleteSingleRateAcp(HashMap<String, Object> data, Acp acp) {
        super(data);
        this.data.put("acpId", acp.get_id());
        this.data.put("side","BOTH");
    }

}
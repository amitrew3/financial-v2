package com.rew3.commission.commissionplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class RemoveAgent extends Command implements ICommand {
    public RemoveAgent(HashMap<String, Object> data) {
        super(data);
    }


}
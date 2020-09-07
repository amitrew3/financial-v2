package com.rew3.brokerage.commissionplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class RemovePreCommission extends Command implements ICommand {
    public RemovePreCommission(HashMap<String, Object> data) {
        super(data);
    }


}
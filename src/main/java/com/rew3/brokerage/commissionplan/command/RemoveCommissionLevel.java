package com.rew3.brokerage.commissionplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class RemoveCommissionLevel extends Command implements ICommand {
    public RemoveCommissionLevel(HashMap<String, Object> data) {
        super(data);
    }




}
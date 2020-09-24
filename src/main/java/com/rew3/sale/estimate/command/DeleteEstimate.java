package com.rew3.sale.estimate.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteEstimate extends Command implements ICommand {
    public String id;


    public DeleteEstimate(String id) {
        this.id = id;

    }
}
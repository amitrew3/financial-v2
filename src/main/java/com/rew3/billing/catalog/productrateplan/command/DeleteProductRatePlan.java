package com.rew3.billing.catalog.productrateplan.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteProductRatePlan extends Command implements ICommand {
    public DeleteProductRatePlan(HashMap<String, Object> data) {
        super(data);

    }
}
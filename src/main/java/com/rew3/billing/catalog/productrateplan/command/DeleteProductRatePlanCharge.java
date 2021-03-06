package com.rew3.billing.catalog.productrateplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

/**
 * Created by amit on 5/16/17.
 */
public class DeleteProductRatePlanCharge extends Command implements ICommand {
    public DeleteProductRatePlanCharge(HashMap<String, Object> data) {
        super(data);
    }

    public DeleteProductRatePlanCharge(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }
}
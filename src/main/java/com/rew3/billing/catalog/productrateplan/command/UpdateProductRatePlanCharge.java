package com.rew3.billing.catalog.productrateplan.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateProductRatePlanCharge extends CreateOrUpdateProductRatePlanAbstract implements ICommand {
    public UpdateProductRatePlanCharge(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/catalog/rateplancharge/update";
        this.validate();
    }

    public UpdateProductRatePlanCharge(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
        this.validationSchema = "billing/catalog/rateplancharge/update";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException();
        }
    }
}
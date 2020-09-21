package com.rew3.sale.recurringinvoice.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;

import java.util.HashMap;

public class UpdateRecurringInvoice extends Command implements ICommand {
    public UpdateRecurringInvoice(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/recurring-invoice/update";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }
    }

    public UpdateRecurringInvoice(HashMap<String, Object> data, String method, RecurringInvoice recurringInvoice) throws Exception {
        super(data);

    }
}
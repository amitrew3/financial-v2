package com.rew3.billing.expense.command;

import com.rew3.billing.expense.model.Expense;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceItem;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.ValidationException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateExpenseItem extends Command implements ICommand {
    public CreateExpenseItem(HashMap<String, Object> data) {
        super(data);
    }

    public CreateExpenseItem(HashMap<String, Object> data, Expense expense) {
        super(data);
        this.data.put("expense", expense);
    }

    public CreateExpenseItem(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }

    public CreateExpenseItem(InvoiceItem data, Transaction trx) throws CommandException, ValidationException {
        super((HashMap<String, Object>) Parser.convert(data), trx);

    }

    public CreateExpenseItem(InvoiceItem item) {

    }
}

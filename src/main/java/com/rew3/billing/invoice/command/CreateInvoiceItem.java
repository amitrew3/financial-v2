package com.rew3.billing.invoice.command;

import java.util.HashMap;

import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceItem;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.ValidationException;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateInvoiceItem extends Command implements ICommand {
    public CreateInvoiceItem(HashMap<String, Object> data) {
        super(data);
    }

    public CreateInvoiceItem(HashMap<String, Object> data, Invoice invoice) {
        super(data);
        this.data.put("invoice", invoice);
    }

    public CreateInvoiceItem(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }

    public CreateInvoiceItem(InvoiceItem data, Transaction trx) throws CommandException, ValidationException {
        super((HashMap<String, Object>) Parser.convert(data), trx);

    }

    public CreateInvoiceItem(InvoiceItem item) {

    }
}

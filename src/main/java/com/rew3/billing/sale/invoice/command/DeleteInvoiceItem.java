package com.rew3.billing.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteInvoiceItem extends Command implements ICommand {
    public DeleteInvoiceItem(HashMap<String, Object> data) {
        super(data);
    }

    public DeleteInvoiceItem(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }

    public DeleteInvoiceItem(String id, Transaction transaction) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
    }
}
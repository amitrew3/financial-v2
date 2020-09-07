package com.rew3.accounting.accountingjournal.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateRefundAccountingJournal extends Command implements ICommand {

    public CreateRefundAccountingJournal(HashMap<String, Object> data) {
        super(data);
    }

    public CreateRefundAccountingJournal(Object invoice, Transaction trx) {
        super(null, trx);
        this.set("invoice", invoice);
    }

    public CreateRefundAccountingJournal(Object invoice) {
        this(invoice, null);
    }
}

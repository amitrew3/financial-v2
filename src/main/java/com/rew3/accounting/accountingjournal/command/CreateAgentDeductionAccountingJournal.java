package com.rew3.accounting.accountingjournal.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class CreateAgentDeductionAccountingJournal extends Command implements ICommand {
    public CreateAgentDeductionAccountingJournal(Double amount, String payerId, String payeeId, Long listingSideAmount
            , Long sellingSideAmount, Transaction trx) {
        super(null, trx);
        this.set("amount", amount);
        this.set("payerId", payerId);
    }

}

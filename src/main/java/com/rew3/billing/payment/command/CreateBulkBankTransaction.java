package com.rew3.billing.payment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateBulkBankTransaction extends Command implements ICommand {
    public CreateBulkBankTransaction(List<HashMap<String, Object>> data) {
        super(data);
       /* this.validationSchema = "billing/payment/transaction/create";
        this.validate();*/

    }

    /*public CreateBulkBankTransaction(Map<String, Object> data, Long billingAccountId, Transaction trx) {
        super(data, trx);
        this.set("billingAccountId", billingAccountId);
    }*/

}
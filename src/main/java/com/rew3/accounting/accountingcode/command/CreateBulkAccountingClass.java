package com.rew3.accounting.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkAccountingClass extends Command implements ICommand {
    public CreateBulkAccountingClass(List<HashMap<String, Object>> data) {
        super(data);
       /* this.validationSchema = "billing/payment/transaction/create";
        this.validate();*/

    }

    /*public CreateBulkBankTransaction(Map<String, Object> data, Long billingAccountId, Transaction trx) {
        super(data, trx);
        this.set("billingAccountId", billingAccountId);
    }*/

}
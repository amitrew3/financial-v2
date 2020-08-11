package com.rew3.billing.catalog.productcategory.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkProductCategory extends Command implements ICommand {
    public CreateBulkProductCategory(List<HashMap<String, Object>> data) {
        super(data);
       /* this.validationSchema = "billing/payment/transaction/create";
        this.validate();*/

    }

    /*public CreateBulkBankTransaction(Map<String, Object> data, Long billingAccountId, Transaction trx) {
        super(data, trx);
        this.set("billingAccountId", billingAccountId);
    }*/

}
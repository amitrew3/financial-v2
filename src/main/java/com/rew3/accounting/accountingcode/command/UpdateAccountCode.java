package com.rew3.accounting.accountingcode.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.accounting.accountingcode.AccountGroupQueryHandler;
import com.rew3.accounting.accountingcode.model.AccountGroup;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;

public class UpdateAccountCode extends Command implements ICommand {
    public UpdateAccountCode(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "finance/accountingcode/update";
        this.validate();

    }

    public UpdateAccountCode(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
        this.validationSchema = "accounting/code/update";
        this.validate();

        if (this.has("entityType")) {
            this.set("entityType", Flags.convertInputToEnum(this.get("entityType"), "EntityType"));
        }

        if (this.has("head")) {
            this.set("head", Flags.convertInputToEnum(this.get("head"), "AccountingHead"));
        }

        if (this.has("segment")) {
            this.set("segment", Flags.convertInputToEnum(this.get("segment"), "AccountingCodeSegment"));
        }
    }

    public UpdateAccountCode(String accountingCodeId, String userId, String accountingCodeType, Flags.AccountingCodeSegment segment, String codeName, Transaction trx) {
        super(null, trx);
        AccountGroupQueryHandler queryHandler = new AccountGroupQueryHandler();
        AccountGroup accountGroup = queryHandler.getSubAccountingHeadByAccountingCodeType(accountingCodeType);
        this.set("subAccountingHeadId", accountGroup.get_id());
        this.set("userId", userId);
        this.set("accountingCodeType", accountingCodeType);
        this.set("segment", segment.toString());
        this.set("name", codeName);
    }

    public UpdateAccountCode(String accountingCodeId, String codeName, Transaction trx) {
        super(null, trx);

        this.set("id", accountingCodeId);
        this.set("name", codeName);
    }


}
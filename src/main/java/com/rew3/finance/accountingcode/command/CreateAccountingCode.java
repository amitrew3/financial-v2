package com.rew3.finance.accountingcode.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.finance.accountingcode.SubAccountingHeadQueryHandler;
import com.rew3.finance.accountingcode.model.SubAccountingHead;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags.AccountingCodeSegment;
import com.rew3.common.model.Flags.AccountingHead;
import com.rew3.common.model.Flags.EntityType;

public class CreateAccountingCode extends Command implements ICommand {
    public CreateAccountingCode(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "finance/accountingcode/create";
        boolean valid=this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }

//		this.set("entityType", Flags.convertInputToEnum(this.get("entityType"), "EntityType"));
//		this.set("head", Flags.convertInputToEnum(this.get("head"), "AccountingHead"));
//		this.set("segment", Flags.convertInputToEnum(this.get("segment"), "AccountingCodeSegment"));
    }

    public CreateAccountingCode(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
        this.validationSchema = "finance/accountingcode/create";
        if (!this.validate()) {
            throw new CommandException("invalid");
        }
//        this.set("entityType", Flags.convertInputToEnum(this.get("entityType"), "EntityType"));
//        this.set("head", Flags.convertInputToEnum(this.get("head"), "AccountingHead"));
//        this.set("segment", Flags.convertInputToEnum(this.get("segment"), "AccountingCodeSegment"));
    }

    public CreateAccountingCode(String userId, AccountingHead head, String entityId, EntityType entityType,
                                AccountingCodeSegment segment) {
        this(userId, head, entityId, entityType, segment, null);
    }

    public CreateAccountingCode(String userId, AccountingHead head, String entityId, EntityType entityType,
                                AccountingCodeSegment segment, Transaction trx) {
        super(null, trx);
        this.set("userId", userId);
        this.set("head", head.toString());
        this.set("entityId", entityId);
        if (entityType != null) {
            this.set("entityType", entityType.toString());
        }
        this.set("segment", segment.toString());
    }

    public CreateAccountingCode(String userId, String accountingCodeType, AccountingCodeSegment segment, String codeName, Transaction trx) {
        super(null, trx);
        SubAccountingHeadQueryHandler queryHandler= new SubAccountingHeadQueryHandler();
        SubAccountingHead subAccountingHead=queryHandler.getSubAccountingHeadByAccountingCodeType(accountingCodeType);
        this.set("subAccountingHeadId", subAccountingHead.get_id());
        this.set("userId", userId);
        this.set("accountingCodeType", accountingCodeType);
        this.set("segment", segment.toString());
        this.set("name", codeName);
    }

    public CreateAccountingCode(String ownerId,Integer subAccountingHeadCode, AccountingCodeSegment segment, String codeName, Transaction trx) {
        super(null, trx);
        SubAccountingHeadQueryHandler queryHandler= new SubAccountingHeadQueryHandler();
        SubAccountingHead subAccountingHead=queryHandler.getSubAccountingHeadByCode(subAccountingHeadCode);
        this.set("subAccountingHeadId", subAccountingHead.get_id());
        this.set("segment", segment.toString());
        this.set("name", codeName);
    }


}
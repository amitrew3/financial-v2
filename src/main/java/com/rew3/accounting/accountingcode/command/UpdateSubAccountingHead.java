package com.rew3.accounting.accountingcode.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateSubAccountingHead extends Command implements ICommand {
	public UpdateSubAccountingHead(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/subaccountinghead/update";
		boolean valid = this.validate();
		if (!valid) {
			throw new CommandException("invalid");
		}

	/*	if (this.has("entityType")) {
			this.set("entityType", Flags.convertInputToEnum(this.get("entityType"), "EntityType"));
		}

		if (this.has("head")) {
			this.set("head", Flags.convertInputToEnum(this.get("head"), "AccountingHead"));
		}

		if (this.has("segment")) {
			this.set("segment", Flags.convertInputToEnum(this.get("segment"), "AccountingCodeSegment"));
		}*/
	}

	public UpdateSubAccountingHead(HashMap<String, Object> data, Transaction trx) throws CommandException {
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
}
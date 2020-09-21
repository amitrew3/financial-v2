package com.rew3.accounting.account.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityType;

public class CreateEntityCode extends Command implements ICommand {
	public CreateEntityCode(HashMap<String, Object> data) {
		this(data, null);
	}

	public CreateEntityCode(HashMap<String, Object> data, Transaction trx) {
		super(data, null);
		this.set("entityType", Flags.convertInputToEnum(this.get("entityType"), "EntityType"));
	}

	public CreateEntityCode(String userId, Long entityId, EntityType entityType,
			String entityTitle) {
		this(userId, entityId, entityType, entityTitle, null);
	}

	public CreateEntityCode(String userId, Long entityId, EntityType entityType,
			String entityTitle, Transaction trx) {
		super(null, trx);
		this.set("userId", userId);
		this.set("entityId", entityId);
		this.set("entityType", entityType);
		this.set("entityTitle", entityTitle);
	}
}
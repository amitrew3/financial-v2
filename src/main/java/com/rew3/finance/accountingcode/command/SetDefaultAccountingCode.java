package com.rew3.finance.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityType;
import org.hibernate.Transaction;

import java.util.HashMap;

public class SetDefaultAccountingCode extends Command implements ICommand {
	public SetDefaultAccountingCode(HashMap<String, Object> data) {
		super(data);
	}


}
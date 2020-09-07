package com.rew3.accounting.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class SetDefaultAccountingCode extends Command implements ICommand {
	public SetDefaultAccountingCode(HashMap<String, Object> data) {
		super(data);
	}


}
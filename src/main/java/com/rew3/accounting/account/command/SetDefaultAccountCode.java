package com.rew3.accounting.account.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class SetDefaultAccountCode extends Command implements ICommand {
	public SetDefaultAccountCode(HashMap<String, Object> data) {
		super(data);
	}


}
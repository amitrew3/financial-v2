package com.rew3.accounting.journal.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeleteJournal extends Command implements ICommand {
	public DeleteJournal(HashMap<String, Object> data) {
		
	}
}
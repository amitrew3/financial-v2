package com.rew3.billing.purchase.debitnote.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateDebitNote extends Command implements ICommand {
    public CreateDebitNote(List<HashMap<String, Object>> data) {
        super(data);
    }
}
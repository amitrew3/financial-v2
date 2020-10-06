package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.recurringtemplate.AddRecurringTemplateProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateRecurringTemplate extends Command implements ICommand {
public AddRecurringTemplateProto addRecurringTemplateProto;

    public CreateRecurringTemplate(AddRecurringTemplateProto addRecurringTemplateProto) {
        this.addRecurringTemplateProto = addRecurringTemplateProto;
    }
}

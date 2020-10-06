package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.recurringtemplate.AddRecurringTemplateProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.UpdateRecurringTemplateProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateRecurringTemplate extends Command implements ICommand {
public UpdateRecurringTemplateProto updateRecurringTemplateProto;

    public UpdateRecurringTemplate(UpdateRecurringTemplateProto updateRecurringTemplateProto) {
        this.updateRecurringTemplateProto = updateRecurringTemplateProto;
    }
}

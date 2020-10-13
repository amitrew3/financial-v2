package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.recurringschedule.UpdateRecurringScheduleProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateRecurringSchedule extends Command implements ICommand {
   public UpdateRecurringScheduleProto updateRecurringScheduleProto;

    public UpdateRecurringSchedule(UpdateRecurringScheduleProto updateRecurringScheduleProto) {
        this.updateRecurringScheduleProto = updateRecurringScheduleProto;
    }
}
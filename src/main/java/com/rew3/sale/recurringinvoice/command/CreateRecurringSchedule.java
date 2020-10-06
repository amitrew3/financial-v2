package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.recurringschedule.AddRecurringScheduleProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateRecurringSchedule extends Command implements ICommand {
public AddRecurringScheduleProto addRecurringScheduleProto;

    public CreateRecurringSchedule(AddRecurringScheduleProto addRecurringScheduleProto) {
        this.addRecurringScheduleProto = addRecurringScheduleProto;
    }
}

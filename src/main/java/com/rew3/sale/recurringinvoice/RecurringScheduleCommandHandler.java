package com.rew3.sale.recurringinvoice;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.recurringschedule.AddRecurringScheduleProto;
import com.avenue.financial.services.grpc.proto.recurringschedule.RecurringScheduleInfoProto;
import com.avenue.financial.services.grpc.proto.recurringschedule.UpdateRecurringScheduleProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.Rew3Validation;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.sale.recurringinvoice.command.CreateRecurringSchedule;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringSchedule;
import com.rew3.sale.recurringinvoice.command.UpdateRecurringSchedule;
import com.rew3.sale.recurringinvoice.model.RecurringSchedule;

public class RecurringScheduleCommandHandler implements ICommandHandler {
    Rew3Validation<RecurringSchedule> rew3Validation = new Rew3Validation<RecurringSchedule>();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateRecurringSchedule.class, RecurringScheduleCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateRecurringSchedule.class, RecurringScheduleCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteRecurringSchedule.class, RecurringScheduleCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateRecurringSchedule) {
            handle((CreateRecurringSchedule) c);
        } else if (c instanceof UpdateRecurringSchedule) {
            handle((UpdateRecurringSchedule) c);
        } else if (c instanceof DeleteRecurringSchedule) {
            handle((DeleteRecurringSchedule) c);
        }
    }

    public void handle(CreateRecurringSchedule c) throws Exception {
        try {
            RecurringSchedule invoice = this._handleSaveRecurringSchedule(c.addRecurringScheduleProto);
            if (invoice != null) {
                c.setObject(invoice);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(UpdateRecurringSchedule c) throws Exception {
        try {
            RecurringSchedule invoice = this._handleUpdateRecurringSchedule(c.updateRecurringScheduleProto);
            if (invoice != null) {
                c.setObject(invoice);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    private RecurringSchedule _handleUpdateRecurringSchedule(UpdateRecurringScheduleProto c) throws Exception {
        RecurringSchedule schedule = null;
        if (c.hasId()) {
            schedule = (RecurringSchedule) new RecurringScheduleQueryHandler().getById(c.getId().getValue());
        }
        RecurringScheduleInfoProto invoiceInfo = null;


        if (c.hasRecurringScheduleInfo()) {
            invoiceInfo = c.getRecurringScheduleInfo();
            if (invoiceInfo.hasTitle()) {
                schedule.setTitle(invoiceInfo.getTitle().getValue());
            }
            schedule.setScheduleType(Flags.RecurringScheduleType.valueOf(invoiceInfo.getScheduleType().name()));

            if (invoiceInfo.hasDayIndex()) {
                schedule.setDayIndex(invoiceInfo.getDayIndex().getValue());
            }
            if (invoiceInfo.hasMonthIndex()) {
                schedule.setDayIndex(invoiceInfo.getDayIndex().getValue());
            }
            if (invoiceInfo.hasWeekDayIndex()) {
                schedule.setWeekDayIndex(invoiceInfo.getWeekDayIndex().getValue());
            }
            if (invoiceInfo.hasCount()) {
                schedule.setCount(invoiceInfo.getCount().getValue());
            }
            if (invoiceInfo.hasDescription()) {
                schedule.setDescription(invoiceInfo.getDescription().getValue());
            }
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                schedule.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                schedule.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                schedule.setOwnerLastName(miniUserProto.getLastName().getValue());
            }
        }
        if (rew3Validation.validateForUpdate(schedule)) {
            schedule = (RecurringSchedule) HibernateUtilV2.update(schedule);
        }
        return schedule;

    }


    private RecurringSchedule _handleSaveRecurringSchedule(AddRecurringScheduleProto c) throws Exception {
        RecurringSchedule schedule = new RecurringSchedule();


        RecurringScheduleInfoProto invoiceInfo = null;


        if (c.hasRecurringScheduleInfo()) {
            invoiceInfo = c.getRecurringScheduleInfo();
            if (invoiceInfo.hasTitle()) {
                schedule.setTitle(invoiceInfo.getTitle().getValue());
            }
            schedule.setScheduleType(Flags.RecurringScheduleType.valueOf(invoiceInfo.getScheduleType().name()));

            if (invoiceInfo.hasDayIndex()) {
                schedule.setDayIndex(invoiceInfo.getDayIndex().getValue());
            }
            if (invoiceInfo.hasMonthIndex()) {
                schedule.setDayIndex(invoiceInfo.getDayIndex().getValue());
            }
            if (invoiceInfo.hasWeekDayIndex()) {
                schedule.setWeekDayIndex(invoiceInfo.getWeekDayIndex().getValue());
            }
            if (invoiceInfo.hasCount()) {
                schedule.setCount(invoiceInfo.getCount().getValue());
            }
            if (invoiceInfo.hasDescription()) {
                schedule.setDescription(invoiceInfo.getDescription().getValue());
            }
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                schedule.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                schedule.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                schedule.setOwnerLastName(miniUserProto.getLastName().getValue());
            }
        }
        if (rew3Validation.validateForAdd(schedule)) {
            schedule = (RecurringSchedule) HibernateUtilV2.update(schedule);
        }
        return schedule;

    }


    public void handle(DeleteRecurringSchedule c) throws NotFoundException, CommandException, JsonProcessingException {

        String id = c.id;
        RecurringSchedule schedule = (RecurringSchedule) new RecurringScheduleQueryHandler().getById(id);

        if (schedule != null) {

            schedule.setStatus(Flags.EntityStatus.DELETED);
            schedule = (RecurringSchedule) HibernateUtilV2.saveAsDeleted(schedule);
        }
        c.setObject(schedule);
    }


}

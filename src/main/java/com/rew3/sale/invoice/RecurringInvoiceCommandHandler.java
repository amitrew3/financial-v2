package com.rew3.sale.invoice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Rew3Date;
import com.rew3.sale.invoice.command.UpdateRecurringInvoice;
import com.rew3.sale.recurringinvoice.command.CreateRecurringInvoice;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;

public class RecurringInvoiceCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateRecurringInvoice.class, RecurringInvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateRecurringInvoice.class, RecurringInvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteRecurringInvoice.class, RecurringInvoiceCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateRecurringInvoice) {
            handle((CreateRecurringInvoice) c);
        } else if (c instanceof UpdateRecurringInvoice) {
            handle((UpdateRecurringInvoice) c);
        } else if (c instanceof DeleteRecurringInvoice) {
            handle((DeleteRecurringInvoice) c);
        }
    }


    public void handle(CreateRecurringInvoice c) throws Exception {
        RecurringInvoice commissionPlan = this._handleSaveRecurringInvoice(c);
        c.setObject(commissionPlan);


    }


    public void handle(UpdateRecurringInvoice c) throws Exception {
        RecurringInvoice commissionPlan = this._handleSaveRecurringInvoice(c);
        c.setObject(commissionPlan);


    }


    private RecurringInvoice _handleSaveRecurringInvoice(ICommand c) throws Exception {

        boolean isNew = true;

        RecurringInvoice recurringInvoice = new RecurringInvoice();

        if (c.has("id")) {

            recurringInvoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(c.get("id").toString());
            isNew = false;
        }
        if (c.has("startDate")) {
            recurringInvoice.setStartDate(Rew3Date.convertToUTC(c.get("startDate").toString()));
        }
        if (c.has("endDate")) {
            recurringInvoice.setEndDate(Rew3Date.convertToUTC(c.get("endDate").toString()));
        }


        if (c.has("status")) {
            recurringInvoice.setStatus(EntityStatus.valueOf(c.get("status").toString().toUpperCase()));
        } else if (isNew) {
            recurringInvoice.setStatus(EntityStatus.ACTIVE);
        }


        recurringInvoice = (RecurringInvoice) HibernateUtils.save(recurringInvoice, c, isNew);

        return recurringInvoice;


    }

    public void handle(DeleteRecurringInvoice c) throws NotFoundException, CommandException, JsonProcessingException {

        RecurringInvoice plan = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(c.get("id").toString());
        if (plan != null) {
            if (!plan.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }
            HibernateUtils.saveAsDeleted(plan);

            c.setObject(plan);
        }

    }

}

package com.rew3.salestax;

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
import com.rew3.salestax.command.CreateSalesTax;
import com.rew3.salestax.command.DeleteSalesTax;
import com.rew3.salestax.command.UpdateSalesTax;
import com.rew3.salestax.model.SalesTax;

public class SalesTaxCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateSalesTax.class, SalesTaxCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateSalesTax.class, SalesTaxCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteSalesTax.class, SalesTaxCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateSalesTax) {
            handle((CreateSalesTax) c);
        } else if (c instanceof UpdateSalesTax) {
            handle((UpdateSalesTax) c);
        } else if (c instanceof DeleteSalesTax) {
            handle((DeleteSalesTax) c);
        }
    }

    public void handle(CreateSalesTax c) throws Exception {
        SalesTax commissionPlan = this._handleSaveSalesTax(c);
        c.setObject(commissionPlan);


    }


    public void handle(UpdateSalesTax c) throws Exception {
        SalesTax commissionPlan = this._handleSaveSalesTax(c);
        c.setObject(commissionPlan);


    }




    private SalesTax _handleSaveSalesTax(ICommand c) throws Exception {

        boolean isNew = true;

        SalesTax term = new SalesTax();

        if (c.has("id")) {

            term = (SalesTax) new SalesTaxQueryHandler().getById(c.get("id").toString());
            isNew = false;
        }

       /* if (c.has("fixedDays")) {
            term.setFixedDays((Integer) c.get("fixedDays"));
        }

        if (c.has("dayOfMonth")) {
            term.setDayOfMonth((Integer) c.get("dayOfMonth"));
        }

        if (c.has("type")) {
            String type = (String) c.get("type");
            term.setDueRuleType(Flags.DueRuleType.valueOf(type.toUpperCase()));
        }
        if (c.has("daysOfDueDate")) {
            term.setDaysOfDueDate((Integer) c.get("daysOfDueDate"));
        }*/

        if (c.has("status")) {
            term.setStatus(EntityStatus.valueOf(c.get("status").toString().toUpperCase()));
        } else if (isNew) {
            term.setStatus(EntityStatus.ACTIVE);
        }


        term = (SalesTax) HibernateUtils.save(term, c, isNew);

        return term;


    }

    public void handle(DeleteSalesTax c) throws NotFoundException, CommandException, JsonProcessingException {

        SalesTax plan = (SalesTax) new SalesTaxQueryHandler().getById(c.get("id").toString());
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

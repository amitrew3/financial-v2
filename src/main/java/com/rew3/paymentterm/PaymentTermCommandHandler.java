package com.rew3.paymentterm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.paymentterm.command.CreateTerm;
import com.rew3.paymentterm.command.DeleteTerm;
import com.rew3.paymentterm.command.UpdateTerm;
import com.rew3.paymentterm.model.PaymentTerm;

public class PaymentTermCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteTerm.class, PaymentTermCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateTerm) {
            handle((CreateTerm) c);
        } else if (c instanceof UpdateTerm) {
            handle((UpdateTerm) c);
        } else if (c instanceof DeleteTerm) {
            handle((DeleteTerm) c);
        }
    }


    public void handle(CreateTerm c) throws Exception {
        PaymentTerm commissionPlan = this._handleSavePaymentTerm(c);
        c.setObject(commissionPlan);


    }


    public void handle(UpdateTerm c) throws Exception {
        PaymentTerm commissionPlan = this._handleSavePaymentTerm(c);
        c.setObject(commissionPlan);


    }


    private PaymentTerm _handleSavePaymentTerm(ICommand c) throws Exception {

        boolean isNew = true;

        PaymentTerm term = new PaymentTerm();

        if (c.has("id")) {

            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.get("id").toString());
            isNew = false;
        }

        term = (PaymentTerm) HibernateUtils.save(term, c, isNew);

        return term;


    }

    public void handle(DeleteTerm c) throws NotFoundException, CommandException, JsonProcessingException {

        PaymentTerm plan = (PaymentTerm) new PaymentTermQueryHandler().getById(c.get("id").toString());
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

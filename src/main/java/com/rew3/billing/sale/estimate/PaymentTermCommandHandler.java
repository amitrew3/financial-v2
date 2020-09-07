package com.rew3.billing.sale.estimate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.sale.estimate.command.*;
import com.rew3.billing.sale.estimate.model.PaymentTerm;
import com.rew3.billing.service.PaymentService;
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
import com.rew3.common.utils.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentTermCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkTerm.class, PaymentTermCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateTerm) {
            handle((CreateTerm) c);
        } else if (c instanceof UpdateTerm) {
            handle((UpdateTerm) c);
        } else if (c instanceof DeleteTerm) {
            handle((DeleteTerm) c);
        } else if (c instanceof CreateBulkTerm) {
            handle((CreateBulkTerm) c);
        } else if (c instanceof UpdateBulkTerm) {
            handle((UpdateBulkTerm) c);
        } else if (c instanceof DeleteBulkTerm) {
            handle((DeleteBulkTerm) c);
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
        if (c.has("name")) {
            term.setName((String) c.get("name"));
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

        if (c.has("value")) {
            term.setValue(Parser.convertObjectToInteger(c.get("value")));
        }
        if (c.has("status")) {
            term.setStatus(EntityStatus.valueOf(c.get("status").toString().toUpperCase()));
        } else if (isNew) {
            term.setStatus(EntityStatus.ACTIVE);
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

    public void handle(CreateBulkTerm c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        PaymentService service = new PaymentService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            PaymentTerm acp = service.createUpdatePaymentTerm(data);

            plans.add(acp);
        }
        c.setObject(plans);

    }

    public void handle(UpdateBulkTerm c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        PaymentService service = new PaymentService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            PaymentTerm invoice = service.createUpdatePaymentTerm(data);
            plans.add(invoice);
        }
        c.setObject(plans);

    }

    public void handle(DeleteBulkTerm c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> plans = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteBulkTerm(map);
            CommandRegister.getInstance().process(command);
            PaymentTerm nu = (PaymentTerm) command.getObject();
            plans.add(nu);
        }

        c.setObject(plans);
    }

}

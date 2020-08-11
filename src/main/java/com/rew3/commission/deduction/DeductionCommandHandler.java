package com.rew3.commission.deduction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.commission.deduction.command.CreateDeduction;
import com.rew3.commission.deduction.command.DeleteDeduction;
import com.rew3.commission.deduction.command.UpdateDeduction;
import com.rew3.commission.deduction.model.Deduction;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import javax.servlet.ServletException;

public class DeductionCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateDeduction.class, DeductionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateDeduction.class, DeductionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteDeduction.class, DeductionCommandHandler.class);
    }

    public void handle(ICommand c) throws CommandException, ServletException, NotFoundException, JsonProcessingException {
        if (c instanceof CreateDeduction) {
            handle((CreateDeduction) c);
        } else if (c instanceof UpdateDeduction) {
            handle((UpdateDeduction) c);
        } else if (c instanceof DeleteDeduction) {
            handle((DeleteDeduction) c);
        }
    }

    public void handle(CreateDeduction c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            Deduction deduction = this._handleSaveDeduction(c);
            if (deduction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(deduction);

                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }


    }


    public void handle(UpdateDeduction c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            Deduction deduction = this._handleSaveDeduction(c);
            if (deduction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(deduction);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    private Deduction _handleSaveDeduction(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException {

        Deduction deduction = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateDeduction) {
            deduction = (Deduction) (new DeductionQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            if (!deduction.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (deduction == null) {
            deduction = new Deduction();
        }

        if (c.has("name")) {
            deduction.setName((String) c.get("name"));
        }

        if (c.has("calculationOption")) {
            Flags.CalculationOption option = Flags.CalculationOption.valueOf(c.get("calculationOption").toString().toUpperCase());
            deduction.setCalculationOption(option);
        }
        if (c.has("deductionType")) {
            Flags.DeductionType deductionType = Flags.DeductionType.valueOf(c.get("deductionType").toString().toUpperCase());
            deduction.setDeductionType(deductionType);
        }
        if (c.has("calculationType")) {
            Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(c.get("calculationType").toString().toUpperCase());
            deduction.setCalculationType(type);
        }
        if (c.has("amount")) {
            deduction.setAmount(Parser.convertObjectToDouble(c.get("ls")));
        }
        if (c.has("side")) {
            Flags.SideOption type = Flags.SideOption.valueOf(c.get("side").toString());
            deduction.setSideOption(type);
        }
        if (c.has("priority")) {
            deduction.setPriority(Parser.convertObjectToInteger(c.get("priority")));
        }
        if (c.has("isDefault")) {
            deduction.setDefault(Parser.convertObjectToBoolean(c.get("isDefault")));
        }

        if (c.has("status")) {
            deduction.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            deduction.setStatus(EntityStatus.ACTIVE);
        }


        deduction = (Deduction) HibernateUtils.save(deduction, c.getTransaction());
        return deduction;

    }


}

package com.rew3.brokerage.gcp;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.rew3.brokerage.gcp.command.*;
import com.rew3.brokerage.gcp.model.Gcp;
import org.hibernate.Transaction;

import javax.servlet.ServletException;


public class GcpCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateGcp.class, GcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateGcp.class, GcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteGcp.class, GcpCommandHandler.class);
    }

    public void handle(ICommand c) throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        if (c instanceof CreateGcp) {
            handle((CreateGcp) c);
        } else if (c instanceof UpdateGcp) {
            handle((UpdateGcp) c);
        } else if (c instanceof DeleteGcp) {
            handle((DeleteGcp) c);
        }

    }


    public void handle(CreateGcp c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            Gcp gcp = this._handleGcp(c);
            if (gcp != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(gcp);

                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }


    }


    public void handle(UpdateGcp c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            Gcp gcp = this._handleGcp(c);
            if (gcp != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(gcp);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    private Gcp _handleGcp(ICommand c) throws CommandException, ServletException, JsonProcessingException, NotFoundException {

        Gcp gcp = null;
        boolean isNew = true;



        if (c.has("id") && c instanceof UpdateGcp) {
            gcp = (Gcp) (new GcpQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            if (!gcp.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (gcp == null) {
            gcp = new Gcp();
        }


        if (c.has("name")) {


            gcp.setName((String) c.get("name"));
        }
        if (c.has("calculationType")) {
            String type = (String) c.get("calculationType");

            gcp.setCalculationType(Flags.BaseCalculationType.valueOf(type.toUpperCase()));
        }


        if (c.has("calculationOption")) {
            String option = (String) c.get("calculationOption");

            gcp.setCalculationOption(Flags.CalculationOption.valueOf(option.toUpperCase()));
        }


        if (c.has("commission")) {
            gcp.setCommission(Parser.convertObjectToDouble(c.get("commission")));
        }
        if (c.has("lsCommission")) {
            gcp.setLsCommission(Parser.convertObjectToDouble(c.get("lsCommission")));
        }
        if (c.has("ssCommission")) {
            gcp.setSsCommission(Parser.convertObjectToDouble(c.get("ssCommission")));
        }

        if (c.has("isDefault")) {
            gcp.setDefault(Parser.convertObjectToBoolean(c.get("isDefault")));
        }
        if (c.has("status")) {
            gcp.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            gcp.setStatus(EntityStatus.ACTIVE);
        }


        gcp = (Gcp) HibernateUtils.save(gcp, c.getTransaction());
        return gcp;

    }

    public void handle(DeleteGcp c) throws NotFoundException, CommandException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            Gcp user = (Gcp) new GcpQueryHandler().getById((String) c.get("id"));
            if (user != null) {
                if (!user.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                user.setStatus(EntityStatus.DELETED);
                user.setLastModifiedAt(DateTime.getCurrentTimestamp());
                HibernateUtils.save(user, trx);
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(user);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
                HibernateUtils.closeSession();
        }
    }

}
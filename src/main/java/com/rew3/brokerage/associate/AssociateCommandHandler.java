package com.rew3.brokerage.associate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.shared.AddressQueryHandler;
import com.rew3.common.shared.model.Address;
import com.rew3.brokerage.associate.command.*;
import com.rew3.brokerage.associate.model.Associate;
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
import org.hibernate.Transaction;

import javax.servlet.ServletException;


public class AssociateCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateAssociate.class, AssociateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAssociate.class, AssociateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAssociate.class, AssociateCommandHandler.class);
    }

    public void handle(ICommand c) throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        if (c instanceof CreateAssociate) {
            handle((CreateAssociate) c);
        } else if (c instanceof UpdateAssociate) {
            handle((UpdateAssociate) c);
        } else if (c instanceof DeleteAssociate) {
            handle((DeleteAssociate) c);
        }

    }


    public void handle(CreateAssociate c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            Associate normaluser = this._handleSaveAssociate(c);
            if (normaluser != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(normaluser);

                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }


    }


    public void handle(UpdateAssociate c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            Associate associate = this._handleSaveAssociate(c);
            if (associate != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(associate);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    private Associate _handleSaveAssociate(ICommand c) throws
            CommandException, ServletException, JsonProcessingException, NotFoundException {

            Associate associate = null;
        boolean isNew = true;
        AddressQueryHandler addressQueryHandler = new AddressQueryHandler();


        if (c.has("id") && c instanceof UpdateAssociate) {
            associate = (Associate) (new AssociateQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            if (!associate.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (associate == null) {
            associate = new Associate();
        }

        if (c.has("firstName")) {
            associate.setFirstName((String) c.get("firstName"));
        }
        if (c.has("middleName")) {
            associate.setMiddleName((String) c.get("middleName"));
        }

        if (c.has("lastName")) {
            associate.setLastName((String) c.get("lastName"));
        }

        if (c.has("email")) {
            associate.setEmail((String) c.get("email"));
        }
        if (c.has("phone")) {
            associate.setPhone((String) c.get("phone"));
        }
        if (c.has("sideOption")) {
            Flags.SideOption side = Flags.SideOption.valueOf((String) c.get("sideOption"));
            associate.setSideOption(side);
        }

        if (c.has("addressId")) {
            Address address = (Address) addressQueryHandler.getById((String) c.get("addressId"));
            associate.setAddress(address);
        }


        if (c.has("status")) {
            associate.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            associate.setStatus(EntityStatus.ACTIVE);
        }


        associate = (Associate) HibernateUtils.save(associate, c.getTransaction());
        return associate;

    }

    public void handle(DeleteAssociate c) {
        Transaction trx = c.getTransaction();


    }
}
package com.rew3.billing.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.shared.command.CreateAddress;
import com.rew3.billing.shared.command.DeleteAddress;
import com.rew3.billing.shared.command.UpdateAddress;
import com.rew3.billing.shared.model.Address;
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
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

public class AddressCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateAddress.class, AddressCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAddress.class, AddressCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAddress.class, AddressCommandHandler.class);
    }

    public void handle(ICommand c) {
        if (c instanceof CreateAddress) {
            handle((CreateAddress) c);
        } else if (c instanceof UpdateAddress) {
            handle((UpdateAddress) c);
        } else if (c instanceof DeleteAddress) {
            handle((DeleteAddress) c);
        }
    }



    public void handle(CreateAddress c) {
        Transaction trx = c.getTransaction();
        try {
            Address address = this._handleSaveAddress(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(address);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(UpdateAddress c) {
        Transaction trx = c.getTransaction();
        try {
            Address address = this._handleSaveAddress(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(address);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    private Address _handleSaveAddress(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {

        Address address = null;
        boolean isNew = true;

        if (c.has("id") && c instanceof UpdateAddress) {
            address = (Address) (new AddressQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            if (address == null) {
                APILogger.add(APILogType.ERROR, "Customer (" + c.get("id") + ") not found.");
                throw new CommandException("Customer (" + c.get("id") + ") not found.");
            }
        }

        if (address == null) {
            address = new Address();
        }

        if (c.has("street")) {
            address.setStreet((String) c.get("street"));
        }

        if (c.has("town")) {
            address.setTown((String) c.get("town"));
        }

        if (c.has("province")) {
            address.setProvince((String) c.get("province"));
        }

        if (c.has("postalCode")) {
            address.setPostalCode((String) c.get("postalCode"));
        }
        if (c.has("country")) {
            address.setCountry((String) c.get("country"));
        }

        if (c.has("status")) {
            address.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            address.setStatus(EntityStatus.ACTIVE);
        }


        address = (Address) HibernateUtils.save(address, c.getTransaction());

        return address;
    }

    public void handle(DeleteAddress c) {
        Transaction trx = c.getTransaction();

        try {
            Address address = (Address) new AddressQueryHandler().getById((String) c.get("id"));
            if (address != null) {
                if (!address.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                address.setStatus(EntityStatus.DELETED);
                HibernateUtils.save(address,trx);
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(address);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }

    }
}

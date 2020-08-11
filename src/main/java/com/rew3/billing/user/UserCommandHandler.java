package com.rew3.billing.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.user.command.CreateUser;
import com.rew3.billing.user.command.UpdateUser;
import com.rew3.billing.user.model.User;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
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

public class UserCommandHandler implements ICommandHandler {

	public static void registerCommands() {
		CommandRegister.getInstance().registerHandler(CreateUser.class, UserCommandHandler.class);
		CommandRegister.getInstance().registerHandler(UpdateUser.class, UserCommandHandler.class);
	}

	public void handle(ICommand c) {
		if (c instanceof CreateUser) {
			handle((CreateUser) c);
		} else if (c instanceof UpdateUser) {
			handle((UpdateUser) c);
		}
	}

	public void handle(CreateUser c) {
		// HibernateUtils.openSession();
		Transaction trx = c.getTransaction();

		try {
			User u = this._handleSaveUser(c);
			if (c.isCommittable()) {
				HibernateUtils.commitTransaction(c.getTransaction());
			}
			c.setObject(u);
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

	public void handle(UpdateUser c) {
		// HibernateUtils.openSession();
		Transaction trx = c.getTransaction();

		try {
			User ba = this._handleSaveUser(c);
			if (c.isCommittable()) {
				HibernateUtils.commitTransaction(c.getTransaction());
			}
			c.setObject(ba);
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

	private User _handleSaveUser(ICommand c) throws CommandException, JsonProcessingException {
		User u = null;
		boolean isNew = true;

		if (c.has("id") && c instanceof UpdateUser) {
			u = (User) (new UserQueryHandler()).getById((String) c.get("id"));
			isNew = false;
			if (u == null) {
				APILogger.add(APILogType.ERROR, "User (" + c.get("id") + ") not found.");
				throw new CommandException("User (" + c.get("id") + ") not found.");
			}
		}

		if (u == null) {
			u = new User();
		}

		if (c.has("firstName")) {
			u.setFirstName((String) c.get("firstName"));
		}

		if (c.has("lastName")) {
			u.setLastName((String) c.get("lastName"));
		}

		if (c.has("email")) {
			u.setEmail((String) c.get("email"));
		}

		if (c.has("phone")) {
			u.setPhone((String) c.get("phone"));
		}

		if (c.has("addressLine1")) {
			u.setAddressLine1((String) c.get("addressLine1"));
		}

		if (c.has("addressLine2")) {
			u.setAddressLine2((String) c.get("addressLine2"));
		}

		if (c.has("city")) {
			u.setCity((String) c.get("city"));
		}

		if (c.has("state")) {
			u.setState((String) c.get("state"));
		}

		if (c.has("country")) {
			u.setCountry((String) c.get("country"));
		}

		if (c.has("postalCode")) {
			u.setPostalCode((String) c.get("postalCode"));
		}

		if (c.has("externalId")) {
			u.setExternalId((String) c.get("externalId"));
		}

		if (c.has("status")) {
			u.setStatus(EntityStatus.valueOf((String) c.get("status")));
		} else if (isNew) {
			u.setStatus(EntityStatus.ACTIVE);
		}



		u = (User) HibernateUtils.save(u, c.getTransaction());

		/*String entityTitle = u.getFirstName() + " " + u.getLastName();

		ICommand userEntityCommand = null;

		// Create user entity code.
		// Entity code is used in creating accounting code.
		if (isNew) {
			userEntityCommand = new CreateEntityCode(u.get_id(), u.get_id(), EntityType.USER, entityTitle,
					c.getTransaction());
		} else {
			userEntityCommand = new UpdateEntityCode(u.get_id(), u.get_id(), EntityType.USER, entityTitle,
					c.getTransaction());
		}

		CommandRegister.getInstance().process(userEntityCommand);
		*//*EntityCode userEntity = (EntityCode) userEntityCommand.getObject();

		if (userEntity == null) {
			APILogger.add(APILogType.ERROR, "Error creating user entity code.");
			throw new CommandException("Error creating user entity code.");
		}
*//*
		// If new user created then create user's gross commission entity code.
		// Entity code is used in creating accounting code.
		if (isNew) {
			ICommand gcEntityCommand = new CreateEntityCode(u.get_id(), 0L, EntityType.COMMISSION, "Gross Commission",
					c.getTransaction());
			CommandRegister.getInstance().process(gcEntityCommand);
			*//*EntityCode gcEntity = (EntityCode) gcEntityCommand.getObject();
			if (gcEntity == null) {
				APILogger.add(APILogType.ERROR, "Error creating user gross commission code.");
				throw new CommandException("Error creating user gross commission code.");
			}*//*
		}
*/
		return u;

	}
}

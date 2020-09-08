package com.rew3.billing.sale.creditnote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.purchase.debitnote.model.DebitNote;
import com.rew3.billing.sale.creditnote.command.CreateCreditNote;
import com.rew3.billing.sale.creditnote.command.DeleteCreditNote;
import com.rew3.billing.sale.creditnote.command.UpdateCreditNote;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.shared.AddressQueryHandler;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import org.hibernate.Transaction;

import javax.servlet.ServletException;


public class CreditNoteCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateCreditNote.class, CreditNoteCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateCreditNote.class, CreditNoteCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteCreditNote.class, CreditNoteCommandHandler.class);
       /* CommandRegister.getInstance().registerHandler(CreateBulkCreditNote.class, CreditNoteCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkCreditNote.class, CreditNoteCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkCreditNote.class, CreditNoteCommandHandler.class);*/
    }

    public void handle(ICommand c) throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        if (c instanceof CreateCreditNote) {
            handle((CreateCreditNote) c);
        } else if (c instanceof UpdateCreditNote) {
            handle((UpdateCreditNote) c);
        } else if (c instanceof DeleteCreditNote) {
            handle((DeleteCreditNote) c);
        }
        /*else if (c instanceof CreateBulkCreditNote) {
            handle((CreateBulkCreditNote) c);
        } else if (c instanceof UpdateBulkCreditNote) {
            handle((UpdateBulkCreditNote) c);
        } else if (c instanceof DeleteBulkCreditNote) {
            handle((DeleteBulkCreditNote) c);
        }*/

    }


    public void handle(CreateCreditNote c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            DebitNote normaluser = this._handleSaveCreditNote(c);
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


    public void handle(UpdateCreditNote c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            DebitNote normaluser = this._handleSaveCreditNote(c);
            if (normaluser != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(normaluser);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    private DebitNote _handleSaveCreditNote(ICommand c) throws
            CommandException, ServletException, JsonProcessingException, NotFoundException {

        DebitNote user = null;
        boolean isNew = true;
        AddressQueryHandler addressQueryHandler = new AddressQueryHandler();


        if (c.has("id") && c instanceof UpdateCreditNote) {
            user = (DebitNote) (new CreditNoteQueryHandler()).getById((String) c.get("id"));
            isNew = false;
          /*  if (user == null) {
                throw new NotFoundException("Normal User (" + c.get("id") + ") not found.");
            }*/
            if (!user.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (user == null) {
            user = new DebitNote();
            //   user.setDefaultAcl();
        }



        if (c.has("status")) {
            user.setStatus(EntityStatus.valueOf((String) c.get("status").toString().toUpperCase()));
        } else if (isNew) {
            user.setStatus(EntityStatus.ACTIVE);
        }


        user = (DebitNote) HibernateUtils.save(user, c.getTransaction());
        return user;

    }

    public void handle(DeleteCreditNote c) throws NotFoundException, CommandException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            DebitNote user = (DebitNote) new CreditNoteQueryHandler().getById((String) c.get("id"));
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

   /* public void handle(CreateBulkCreditNote c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> normalusers = (List<HashMap<String, Object>>) c.getBulkData();

        List<Object> normalUsers = new ArrayList<Object>();

        try {

            for (HashMap<String, Object> data : normalusers) {
                ICommand command = new CreateCreditNote(data, trx);
                CommandRegister.getInstance().process(command);
                DebitNote nu = (DebitNote) command.getObject();
                normalUsers.add(nu);
            }
            c.setObject(normalUsers);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }

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

    public void handle(UpdateBulkCreditNote c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateCreditNote(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                c.setObject("Bulk product category updated");
            }

        } catch (Exception ex) {
            ex.printStackTrace();

           *//* if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }*//*
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(DeleteBulkCreditNote c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                map.put("id", id);
                CommandRegister.getInstance().process(new DeleteCreditNote(map, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Normal users deleted");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }*/
}
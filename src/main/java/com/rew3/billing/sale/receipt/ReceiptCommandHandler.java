package com.rew3.billing.sale.receipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.sale.receipt.command.*;
import com.rew3.billing.sale.receipt.model.Receipt;
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
import java.util.HashMap;
import java.util.List;


public class ReceiptCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateReceipt.class, ReceiptCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateReceipt.class, ReceiptCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteReceipt.class, ReceiptCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkReceipt.class, ReceiptCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkReceipt.class, ReceiptCommandHandler.class);
    }

    public void handle(ICommand c) throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        if (c instanceof CreateReceipt) {
            handle((CreateReceipt) c);
        } else if (c instanceof UpdateReceipt) {
            handle((UpdateReceipt) c);
        } else if (c instanceof DeleteReceipt) {
            handle((DeleteReceipt) c);
        } else if (c instanceof CreateReceipt) {
            handle((CreateReceipt) c);
        } else if (c instanceof CreateBulkReceipt) {
            handle((CreateBulkReceipt) c);
        } else if (c instanceof DeleteBulkReceipt) {
            handle((DeleteBulkReceipt) c);
        }

    }


    public void handle(CreateReceipt c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            Receipt normaluser = this._handleSaveNormalUser(c);
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


    public void handle(UpdateReceipt c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            Receipt normaluser = this._handleSaveNormalUser(c);
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

    private Receipt _handleSaveNormalUser(ICommand c) throws
            CommandException, ServletException, JsonProcessingException, NotFoundException {

        Receipt receipt = null;
        boolean isNew = true;
        AddressQueryHandler addressQueryHandler = new AddressQueryHandler();


        if (c.has("id") && c instanceof UpdateReceipt) {
            receipt = (Receipt) (new ReceiptQueryHandler()).getById((String) c.get("id"));
            isNew = false;
          /*  if (receipt == null) {
                throw new NotFoundException("Normal User (" + c.get("id") + ") not found.");
            }*/
            if (!receipt.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (receipt == null) {
            receipt = new Receipt();
            //   receipt.setDefaultAcl();
        }

        if (c.has("firstName")) {
            receipt.setFirstName((String) c.get("firstName"));
        }
        if (c.has("middleName")) {
            receipt.setMiddleName((String) c.get("middleName"));
        }
        if (c.has("title")) {
            receipt.setTitle((String) c.get("title"));
        }
        if (c.has("lastName")) {
            receipt.setLastName((String) c.get("lastName"));
        }


        if (c.has("status")) {
            receipt.setStatus(EntityStatus.valueOf((String) c.get("status").toString().toUpperCase()));
        } else if (isNew) {
            receipt.setStatus(EntityStatus.ACTIVE);
        }


        receipt = (Receipt) HibernateUtils.save(receipt, c.getTransaction());
        return receipt;

    }

    public void handle(DeleteReceipt c) throws NotFoundException, CommandException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            Receipt user = (Receipt) new ReceiptQueryHandler().getById((String) c.get("id"));
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


    public void handle(CreateBulkReceipt c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateReceipt(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                c.setObject("Bulk product category updated");
            }

        } catch (Exception ex) {
            ex.printStackTrace();

           /* if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }*/
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(DeleteBulkReceipt c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                map.put("id", id);
                CommandRegister.getInstance().process(new DeleteReceipt(map, trx));
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


    }
}
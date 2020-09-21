package com.rew3.payment.billpayment;

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
import com.rew3.payment.billpayment.command.CreateBillPayment;
import com.rew3.payment.billpayment.command.DeleteBillPayment;
import com.rew3.payment.billpayment.command.UpdateBillPayment;
import com.rew3.payment.billpayment.model.BillPayment;
import org.hibernate.Transaction;

public class BillPaymentCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateBillPayment.class, BillPaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBillPayment.class, BillPaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBillPayment.class, BillPaymentCommandHandler.class);

    }

    public void handle(ICommand c) {
        if (c instanceof CreateBillPayment) {
            handle((CreateBillPayment) c);
        } else if (c instanceof UpdateBillPayment) {
            handle((UpdateBillPayment) c);
        } else if (c instanceof DeleteBillPayment) {
            handle((DeleteBillPayment) c);
        }
    }

    public void handle(CreateBillPayment c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            BillPayment t = this._handleSaveBillPayment(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(t);
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

    public void handle(UpdateBillPayment c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            BillPayment term = this._handleSaveBillPayment(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(term);
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

    private BillPayment _handleSaveBillPayment(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {

        BillPayment billPayment = null;
        boolean isNew = true;

        if (c.has("id") && c instanceof UpdateBillPayment) {
            billPayment = (BillPayment) (new BillPaymentQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            /*if (paymentOption == null) {
                APILogger.add(APILogType.ERROR, "BillPayment (" + c.get("id") + ") not found.");
                throw new NotFoundException("BillPayment (" + c.get("id") + ") not found.");
            }*/
           /* if(!paymentOption.getW().contains(Authentication.getRew3UserId()) | !paymentOption.getOwnerId().toString().equals(Authentication.getRew3UserId())){
                APILogger.add(APILogType.ERROR, "Access Denied");
                throw new CommandException("Access Denied");
            }*/
        }

        if (billPayment == null) {
            billPayment = new BillPayment();
        }

//        if (c.has("name")) {
//            billPayment.setName((String) c.get("name"));
//        }
//
//        if (c.has("description")) {
//            billPayment.setDescription((String) c.get("description"));
//        }

        if (c.has("status")) {
            billPayment.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            billPayment.setStatus(EntityStatus.ACTIVE);
        }

        billPayment = (BillPayment) HibernateUtils.save(billPayment, c.getTransaction());

        return billPayment;

    }

    public void handle(DeleteBillPayment c) {
        Transaction trx = c.getTransaction();

        try {
            BillPayment terms = (BillPayment) new BillPaymentQueryHandler().getById((String) c.get("id"));
            if (terms != null) {
                if (!terms.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                terms.setStatus(EntityStatus.DELETED);
                terms= (BillPayment) HibernateUtils.save(terms,trx);
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(terms);
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

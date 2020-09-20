package com.rew3.paymentoption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.paymentoption.model.PaymentOption;
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
import org.hibernate.Transaction;

public class PaymentOptionCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreatePaymentOption.class, PaymentOptionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdatePaymentOption.class, PaymentOptionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeletePaymentOption.class, PaymentOptionCommandHandler.class);

    }

    public void handle(ICommand c) {
        if (c instanceof CreatePaymentOption) {
            handle((CreatePaymentOption) c);
        } else if (c instanceof UpdatePaymentOption) {
            handle((UpdatePaymentOption) c);
        } else if (c instanceof DeletePaymentOption) {
            handle((DeletePaymentOption) c);
        }
    }

    public void handle(CreatePaymentOption c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            PaymentOption t = this._handleSavePaymentOption(c);
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

    public void handle(UpdatePaymentOption c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            PaymentOption term = this._handleSavePaymentOption(c);
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

    private PaymentOption _handleSavePaymentOption(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {

        PaymentOption paymentOption = null;
        boolean isNew = true;

        if (c.has("id") && c instanceof UpdatePaymentOption) {
            paymentOption = (PaymentOption) (new PaymentOptionQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            /*if (paymentOption == null) {
                APILogger.add(APILogType.ERROR, "PaymentOption (" + c.get("id") + ") not found.");
                throw new NotFoundException("PaymentOption (" + c.get("id") + ") not found.");
            }*/
           /* if(!paymentOption.getW().contains(Authentication.getRew3UserId()) | !paymentOption.getOwnerId().toString().equals(Authentication.getRew3UserId())){
                APILogger.add(APILogType.ERROR, "Access Denied");
                throw new CommandException("Access Denied");
            }*/
        }

        if (paymentOption == null) {
            paymentOption = new PaymentOption();
        }

        if (c.has("name")) {
            paymentOption.setName((String) c.get("name"));
        }

        if (c.has("description")) {
            paymentOption.setDescription((String) c.get("description"));
        }

        if (c.has("status")) {
            paymentOption.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            paymentOption.setStatus(EntityStatus.ACTIVE);
        }

        paymentOption = (PaymentOption) HibernateUtils.save(paymentOption, c.getTransaction());

        return paymentOption;

    }

    public void handle(DeletePaymentOption c) {
        Transaction trx = c.getTransaction();

        try {
            PaymentOption terms = (PaymentOption) new PaymentOptionQueryHandler().getById((String) c.get("id"));
            if (terms != null) {
                if (!terms.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                terms.setStatus(EntityStatus.DELETED);
                terms= (PaymentOption) HibernateUtils.save(terms,trx);
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

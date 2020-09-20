package com.rew3.payment.invoicepayment;

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
import com.rew3.payment.invoicepayment.command.CreateInvoicePayment;
import com.rew3.payment.invoicepayment.command.DeleteInvoicePayment;
import com.rew3.payment.invoicepayment.command.UpdateInvoicePayment;
import com.rew3.payment.invoicepayment.model.InvoicePayment;
import org.hibernate.Transaction;

public class InvoicePaymentCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateInvoicePayment.class, InvoicePaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateInvoicePayment.class, InvoicePaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteInvoicePayment.class, InvoicePaymentCommandHandler.class);

    }

    public void handle(ICommand c) {
        if (c instanceof CreateInvoicePayment) {
            handle((CreateInvoicePayment) c);
        } else if (c instanceof UpdateInvoicePayment) {
            handle((UpdateInvoicePayment) c);
        } else if (c instanceof DeleteInvoicePayment) {
            handle((DeleteInvoicePayment) c);
        }
    }

    public void handle(CreateInvoicePayment c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            InvoicePayment t = this._handleSaveInvoicePayment(c);
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

    public void handle(UpdateInvoicePayment c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            InvoicePayment term = this._handleSaveInvoicePayment(c);
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

    private InvoicePayment _handleSaveInvoicePayment(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {

        InvoicePayment paymentOption = null;
        boolean isNew = true;

        if (c.has("id") && c instanceof UpdateInvoicePayment) {
            paymentOption = (InvoicePayment) (new InvoicePaymentQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            /*if (paymentOption == null) {
                APILogger.add(APILogType.ERROR, "InvoicePayment (" + c.get("id") + ") not found.");
                throw new NotFoundException("InvoicePayment (" + c.get("id") + ") not found.");
            }*/
           /* if(!paymentOption.getW().contains(Authentication.getRew3UserId()) | !paymentOption.getOwnerId().toString().equals(Authentication.getRew3UserId())){
                APILogger.add(APILogType.ERROR, "Access Denied");
                throw new CommandException("Access Denied");
            }*/
        }

        if (paymentOption == null) {
            paymentOption = new InvoicePayment();
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

        paymentOption = (InvoicePayment) HibernateUtils.save(paymentOption, c.getTransaction());

        return paymentOption;

    }

    public void handle(DeleteInvoicePayment c) {
        Transaction trx = c.getTransaction();

        try {
            InvoicePayment terms = (InvoicePayment) new InvoicePaymentQueryHandler().getById((String) c.get("id"));
            if (terms != null) {
                if (!terms.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                terms.setStatus(EntityStatus.DELETED);
                terms= (InvoicePayment) HibernateUtils.save(terms,trx);
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

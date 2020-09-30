package com.rew3.payment.invoicepayment;

import com.avenue.financial.services.grpc.proto.invoicepayment.AddInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.UpdateInvoicePaymentProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
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

    public void handle(ICommand c) throws NotFoundException, CommandException {
        if (c instanceof CreateInvoicePayment) {
            handle((CreateInvoicePayment) c);
        } else if (c instanceof UpdateInvoicePayment) {
            handle((UpdateInvoicePayment) c);
        } else if (c instanceof DeleteInvoicePayment) {
            handle((DeleteInvoicePayment) c);
        }
    }

    public void handle(CreateInvoicePayment c) {

        try {
            InvoicePayment t = this._handleSaveInvoicePayment(c.addInvoicePaymentProto);
            c.setObject(t);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void handle(UpdateInvoicePayment c) {
        // HibernateUtilV2.openSession();
        Transaction trx = c.getTransaction();

        try {
            InvoicePayment payment = this._handleUpdateInvoicePayment(c.updateInvoicePaymentProto);
            c.setObject(payment);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private InvoicePayment _handleUpdateInvoicePayment(UpdateInvoicePaymentProto c) {
        return new InvoicePayment();
    }

    private InvoicePayment _handleSaveInvoicePayment(AddInvoicePaymentProto c) throws  JsonProcessingException {

        InvoicePayment  invoicePayment = new InvoicePayment();

        if (invoicePayment == null) {
            invoicePayment = new InvoicePayment();
        }



        invoicePayment = (InvoicePayment) HibernateUtilV2.save(invoicePayment);

        return invoicePayment;

    }

    public void handle(DeleteInvoicePayment c) throws NotFoundException, CommandException {
        String id = c.id;
        InvoicePayment invoice = (InvoicePayment) new InvoicePaymentQueryHandler().getById(id);
        c.setObject(invoice);

    }


}

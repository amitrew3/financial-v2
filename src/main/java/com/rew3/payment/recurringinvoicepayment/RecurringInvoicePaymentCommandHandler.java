package com.rew3.payment.recurringinvoicepayment;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.AddInvoicePaymentInfoProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.AddInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.UpdateInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.AddRecurringInvoicePaymentInfoProto;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.AddRecurringInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.UpdateRecurringInvoicePaymentProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.utils.Rew3Date;
import com.rew3.payment.recurringinvoicepayment.command.CreateRecurringInvoicePayment;
import com.rew3.payment.recurringinvoicepayment.command.DeleteRecurringInvoicePayment;
import com.rew3.payment.recurringinvoicepayment.command.UpdateRecurringInvoicePayment;
import com.rew3.payment.recurringinvoicepayment.model.RecurringInvoicePayment;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.recurringinvoice.RecurringInvoiceQueryHandler;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;

public class RecurringInvoicePaymentCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateRecurringInvoicePayment.class, RecurringInvoicePaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateRecurringInvoicePayment.class, RecurringInvoicePaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteRecurringInvoicePayment.class, RecurringInvoicePaymentCommandHandler.class);

    }

    public void handle(ICommand c) throws NotFoundException, CommandException {
        if (c instanceof CreateRecurringInvoicePayment) {
            handle((CreateRecurringInvoicePayment) c);
        } else if (c instanceof UpdateRecurringInvoicePayment) {
            handle((UpdateRecurringInvoicePayment) c);
        } else if (c instanceof DeleteRecurringInvoicePayment) {
            handle((DeleteRecurringInvoicePayment) c);
        }
    }

    public void handle(CreateRecurringInvoicePayment c) {

        try {
            RecurringInvoicePayment t = this._handleSaveInvoicePayment(c.addRecurringInvoicePaymentProto);
            c.setObject(t);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void handle(UpdateRecurringInvoicePayment c) {

        try {
            RecurringInvoicePayment payment = this._handleUpdateInvoicePayment(c.updateRecurringInvoicePaymentProto);
            c.setObject(payment);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private RecurringInvoicePayment _handleUpdateInvoicePayment(UpdateRecurringInvoicePaymentProto c) throws NotFoundException, CommandException, JsonProcessingException {
        RecurringInvoicePayment payment = null;
        if (c.hasId()) {
            payment = (RecurringInvoicePayment) new RecurringInvoicePaymentQueryHandler().getById(c.getId().getValue());
        }

        AddRecurringInvoicePaymentInfoProto invoicePaymentInfo = null;


        if (c.hasInvoicePaymentInfo()) {
            invoicePaymentInfo = c.getInvoicePaymentInfo();
            if (invoicePaymentInfo.hasAmount()) {
                payment.setAmount(invoicePaymentInfo.getAmount().getValue());
            }
            if (invoicePaymentInfo.hasDate()) {
                payment.setDate(Rew3Date.convertToUTC((String) invoicePaymentInfo.getDate().getValue()));
            }
        }

        if (invoicePaymentInfo.hasNotes()) {
            payment.setNotes(invoicePaymentInfo.getNotes().getValue());
        }


        if (invoicePaymentInfo.hasCustomerId()) {
            Customer customer = (Customer) new CustomerQueryHandler().getById(invoicePaymentInfo.getCustomerId().getValue());
            payment.setCustomer(customer);
        }
        if (invoicePaymentInfo.hasInvoiceId()) {
            RecurringInvoice invoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(invoicePaymentInfo.getInvoiceId().getValue());
            payment.setRecurringInvoice(invoice);
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                payment.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                payment.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                payment.setOwnerLastName(miniUserProto.getLastName().getValue());
            }
        }
        payment = (RecurringInvoicePayment) HibernateUtilV2.update(payment);
        return payment;
    }

    private RecurringInvoicePayment _handleSaveInvoicePayment(AddRecurringInvoicePaymentProto c) throws JsonProcessingException, NotFoundException, CommandException {

        RecurringInvoicePayment payment = new RecurringInvoicePayment();
        AddRecurringInvoicePaymentInfoProto invoiceInfo = null;




        if (c.hasInvoicePaymentInfo()) {
            invoiceInfo = c.getInvoicePaymentInfo();
            if (invoiceInfo.hasAmount()) {
                payment.setAmount(invoiceInfo.getAmount().getValue());
            }
            if (invoiceInfo.hasDate()) {
                payment.setDate(Rew3Date.convertToUTC((String) invoiceInfo.getDate().getValue()));
            }
            }

            if (invoiceInfo.hasNotes()) {
                payment.setNotes(invoiceInfo.getNotes().getValue());
            }


            if (invoiceInfo.hasCustomerId()) {
                Customer customer = (Customer) new CustomerQueryHandler().getById(invoiceInfo.getCustomerId().getValue());
                payment.setCustomer(customer);
            }
        if (invoiceInfo.hasInvoiceId()) {
            RecurringInvoice recurringInvoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(invoiceInfo.getInvoiceId().getValue());
            payment.setRecurringInvoice(recurringInvoice);
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                payment.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                payment.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                payment.setOwnerLastName(miniUserProto.getLastName().getValue());
            }
        }
        payment = (RecurringInvoicePayment) HibernateUtilV2.save(payment);
        return payment;

    }

    public void handle(DeleteRecurringInvoicePayment c) throws NotFoundException, CommandException {
        String id = c.id;
        RecurringInvoicePayment invoice = (RecurringInvoicePayment) new RecurringInvoicePaymentQueryHandler().getById(id);
        c.setObject(invoice);

    }


}

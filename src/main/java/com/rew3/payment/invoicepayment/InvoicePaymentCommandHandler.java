package com.rew3.payment.invoicepayment;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.AddInvoicePaymentInfoProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.AddInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.UpdateInvoicePaymentProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.Rew3Validation;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.utils.Rew3Date;
import com.rew3.payment.invoicepayment.command.CreateInvoicePayment;
import com.rew3.payment.invoicepayment.command.DeleteInvoicePayment;
import com.rew3.payment.invoicepayment.command.UpdateInvoicePayment;
import com.rew3.payment.invoicepayment.model.InvoicePayment;
import com.rew3.purchase.expense.model.Expense;
import com.rew3.purchase.vendor.VendorQueryHandler;
import com.rew3.purchase.vendor.model.Vendor;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.recurringinvoice.model.RecurringSchedule;
import org.hibernate.Transaction;

public class InvoicePaymentCommandHandler implements ICommandHandler {
    Rew3Validation<InvoicePayment> rew3Validation = new Rew3Validation<InvoicePayment>();

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

        try {
            InvoicePayment payment = this._handleUpdateInvoicePayment(c.updateInvoicePaymentProto);
            c.setObject(payment);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private InvoicePayment _handleUpdateInvoicePayment(UpdateInvoicePaymentProto c) throws NotFoundException, CommandException, JsonProcessingException {
        InvoicePayment payment = null;
        if (c.hasId()) {
            payment = (InvoicePayment) new InvoicePaymentQueryHandler().getById(c.getId().getValue());
        }

        AddInvoicePaymentInfoProto invoicePaymentInfo = null;


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
            Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(invoicePaymentInfo.getInvoiceId().getValue());
            payment.setInvoice(invoice);
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                payment.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                payment.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                payment.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        if (rew3Validation.validateForUpdate(payment)) {
            payment = (InvoicePayment) HibernateUtilV2.save(payment);
        }        return payment;
    }

    private InvoicePayment _handleSaveInvoicePayment(AddInvoicePaymentProto c) throws JsonProcessingException, NotFoundException, CommandException {

        InvoicePayment payment = new InvoicePayment();
        AddInvoicePaymentInfoProto invoiceInfo = null;




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
            Invoice customer = (Invoice) new InvoiceQueryHandler().getById(invoiceInfo.getInvoiceId().getValue());
            payment.setInvoice(customer);
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                payment.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                payment.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                payment.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        payment = (InvoicePayment) HibernateUtilV2.save(payment);
        return payment;

    }

    public void handle(DeleteInvoicePayment c) throws NotFoundException, CommandException {
        String id = c.id;
        InvoicePayment payment = (InvoicePayment) new InvoicePaymentQueryHandler().getById(id);
        c.setObject(payment);

    }


}

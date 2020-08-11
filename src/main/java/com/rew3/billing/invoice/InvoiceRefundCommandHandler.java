package com.rew3.billing.invoice;

import com.rew3.finance.accountingjournal.command.CreateRefundAccountingJournal;
import org.hibernate.Transaction;

import com.rew3.billing.invoice.command.CreateInvoiceRefundRequest;
import com.rew3.billing.invoice.command.CreateInvoiceRefundResponse;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceRequest;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Parser;

public class InvoiceRefundCommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateInvoiceRefundRequest.class,
                InvoiceRefundCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateInvoiceRefundResponse.class,
                InvoiceRefundCommandHandler.class);
    }

    public void handle(ICommand c) {
        if (c instanceof CreateInvoiceRefundRequest) {
            handle((CreateInvoiceRefundRequest) c);
        } else if (c instanceof CreateInvoiceRefundResponse) {
            handle((CreateInvoiceRefundResponse) c);
        }
    }

    public void handle(CreateInvoiceRefundRequest c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            String invoiceId = (String) c.get("invoiceId");
            Invoice invoice = (Invoice) (new InvoiceQueryHandler()).getById(invoiceId);

            invoice.setRefundStatus(Flags.InvoiceRefundStatus.REFUND_REQUESTED);
            HibernateUtils.save(invoice, trx);

            // Create invoice request entry
            InvoiceRequest ir = new InvoiceRequest();
            ir.setInvoice(invoice);

            if (c.has("amount")) {
                ir.setAmount(Parser.convertObjectToDouble(c.get("amount")));
            }

            ir.setRefundType(Flags.convertInputToFlag(c.get("type"), "InvoiceRefundType"));

            ir.setDescription((String) c.get("description"));

            HibernateUtils.save(ir, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(ir);

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

    public void handle(CreateInvoiceRefundResponse c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            String requestId = (String) c.get("requestId");
            InvoiceRequest iRefundRequest = (new InvoiceQueryHandler()).getRequestById(requestId);
            Flags.InvoiceRefundStatus iRefundResponseStatus = (Flags.InvoiceRefundStatus) Flags.InvoiceRefundStatus.valueOf((String) c.get("action").toString().toUpperCase());


            if (iRefundRequest == null) {
                APILogger.add(APILogType.ERROR, "Invalid refund request id");
                throw new CommandException();
            } else if (!iRefundRequest.getAction().equals(Flags.InvoiceRefundStatus.REFUND_REQUESTED)) {
                APILogger.add(APILogType.ERROR, "Invalid refund request");
                throw new CommandException();
            } else if (iRefundRequest.getStatus().equals(EntityStatus.IN_ACTIVE)) {
                APILogger.add(APILogType.ERROR, "Refund request is inactive");
                throw new CommandException();
            } else if (iRefundRequest.getInvoice() == null) {
                APILogger.add(APILogType.ERROR, "Invoice id is not defined");
                throw new CommandException();
            }

            String invoiceId = iRefundRequest.getInvoice().get_id();
            Invoice invoice = (Invoice) (new InvoiceQueryHandler()).getById(invoiceId);
            invoice.setRefundStatus(iRefundResponseStatus);

            HibernateUtils.save(invoice, trx);

            //Save Accounting Entries
            ICommand refundCommand = new CreateRefundAccountingJournal(invoice, trx);
            CommandRegister.getInstance().process(refundCommand);
            boolean accountingEntryCreated = (boolean) refundCommand.getObject();
            // If accounting journal entry is not created.
            if (!accountingEntryCreated) {
                throw new CommandException();
            }

            // Create invoice request entry
            InvoiceRequest ir = new InvoiceRequest();
            ir.setInvoice(invoice);
            ir.setInvoiceResponseStatus(iRefundResponseStatus);
            ir.setParentId(iRefundRequest.get_id());
            if (c.has("description")) {
                ir.setDescription((String) c.get("description"));
            }

            HibernateUtils.save(ir, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(ir);

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

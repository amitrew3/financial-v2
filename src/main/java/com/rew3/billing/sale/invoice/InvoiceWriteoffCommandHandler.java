package com.rew3.billing.sale.invoice;

import org.hibernate.Transaction;

import com.rew3.billing.sale.invoice.command.CreateInvoiceWriteoff;
import com.rew3.billing.sale.invoice.model.Invoice;
import com.rew3.billing.sale.invoice.model.InvoiceRequest;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.accounting.accountingjournal.command.CreateWriteoffAccountingJournal;

public class InvoiceWriteoffCommandHandler {

	public static void registerCommands() {
		CommandRegister.getInstance().registerHandler(CreateInvoiceWriteoff.class, InvoiceWriteoffCommandHandler.class);
		}

	public void handle(ICommand c) {
		if (c instanceof CreateInvoiceWriteoff) {
			handle((CreateInvoiceWriteoff) c);
		}
	}

	public void handle(CreateInvoiceWriteoff c) {
		//HibernateUtils.openSession();
		Transaction trx = c.getTransaction();

		try {
			String invoiceId = (String) c.get("invoiceId");
			Invoice invoice = (Invoice) (new InvoiceQueryHandler()).getById(invoiceId);

			// If invoice id is not valid
			if (invoice == null) {
				APILogger.add(APILogType.ERROR, "Invoice id (" + invoiceId + ") is not valid.");
				throw new CommandException();
			} else if (!invoice.getPaymentStatus().equals(Flags.InvoicePaymentStatus.UNPAID)
					&& !invoice.getPaymentStatus().equals(Flags.InvoicePaymentStatus.PARTIAL_PAID)) {
				APILogger.add(APILogType.ERROR, "Invoice id (" + invoiceId + ") can not be written off.");
				throw new CommandException();
			}

			// Create invoice request entry
			InvoiceRequest ir = new InvoiceRequest();
			ir.setInvoice(invoice);
			//ir.setStatus(Entity.WRITTEN_OFF);
			ir.setCreatedAt(DateTime.getCurrentTimestamp());

			HibernateUtils.save(ir, trx);

			ICommand writeoffCommand = new CreateWriteoffAccountingJournal(invoice, trx);
			CommandRegister.getInstance().process(writeoffCommand);
			boolean accountingEntryCreated = (boolean) writeoffCommand.getObject();

			// If accounting journal entry is not created.
			if (!accountingEntryCreated) {
				throw new CommandException();
			}

			invoice.setWriteOffStatus(Flags.InvoiceWriteOffStatus.WRITTEN_OFF);
			HibernateUtils.save(invoice, trx);

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

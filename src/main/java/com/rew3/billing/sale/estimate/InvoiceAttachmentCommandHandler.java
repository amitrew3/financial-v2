package com.rew3.billing.sale.estimate;/*
package com.rew3.billing.sales.invoice;

import org.hibernate.Transaction;

import com.rew3.billing.sales.invoice.command.CreateInvoiceAttachment;
import com.rew3.billing.sales.invoice.command.DeleteInvoiceAttachment;
import com.rew3.common.application.Authentication;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.IO;
import com.rew3.common.utils.Parser;

public class InvoiceAttachmentCommandHandler {

	public static void registerCommands() {

		CommandRegister.getInstance().registerHandler(CreateInvoiceAttachment.class, InvoiceAttachmentCommandHandler.class);
//		CommandRegister.getInstance().registerHandler(UpdateInvoiceAttachment.class, InvoiceAttachmentCommandHandler.class);
		CommandRegister.getInstance().registerHandler(DeleteInvoiceAttachment.class, InvoiceAttachmentCommandHandler.class);
	}

	public void handle(ICommand c) {
		if (c instanceof CreateInvoiceAttachment) {
			handle((CreateInvoiceAttachment) c);
//		} else if (c instanceof UpdateInvoiceAttachment) {
//			handle((UpdateInvoiceAttachment) c);
		} else if (c instanceof DeleteInvoiceAttachment) {
			handle((DeleteInvoiceAttachment) c);
		}
	}
	
	public void handle(CreateInvoiceAttachment c) {
		//HibernateUtils.openSession();
		Transaction trx = c.getTransaction();

		try {
			InvoiceAttachment a = new InvoiceAttachment();
			//a.setInvoiceId(Parser.convertObjectToLong(c.get("invoiceId")));
			a.setFileName((String) c.get("filename"));
			a.setOwnerId(Authentication.getUserId());
			a.setCreatedAt(DateTime.getCurrentTimestamp());
			HibernateUtils.save(a, trx);

			if (c.isCommittable()) {
				HibernateUtils.commitTransaction(trx);
			}

			c.setObject(a);

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

	public void handle(DeleteInvoiceAttachment c) {
		//HibernateUtils.openSession();
		Transaction trx = c.getTransaction();

		try {
			boolean result = false;
			Long attachmentId = Parser.convertObjectToLong(c.get("id"));

			InvoiceAttachment iAttachment = (InvoiceAttachment) HibernateUtils.get(InvoiceAttachment.class,
					attachmentId);

			if (iAttachment != null) {
				String filename = iAttachment.getFileName();
				result = HibernateUtils.delete(iAttachment, trx);
				result = IO.deleteAttachment(filename, "invoice");
			}

			if (c.isCommittable()) {
				HibernateUtils.commitTransaction(trx);
			}

			c.setObject(result);
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
*/

package com.rew3.common.application;

import com.rew3.accounting.account.AccountCodeCommandHandler;
import com.rew3.accounting.accountperiod.AccountPeriodCommandHandler;
import com.rew3.accounting.journal.JournalCommandHandler;
import com.rew3.billing.salesv1.SalesEventHandler;
import com.rew3.brokerage.acp.AcpCommandHandler;
import com.rew3.brokerage.associate.AssociateCommandHandler;
import com.rew3.brokerage.commissionplan.CommissionPlanCommandHandler;
import com.rew3.brokerage.deduction.DeductionCommandHandler;
import com.rew3.brokerage.gcp.GcpCommandHandler;
import com.rew3.brokerage.transaction.TransactionCommandHandler;
import com.rew3.catalog.product.ProductCommandHandler;
import com.rew3.common.shared.AclCommandHandler;
import com.rew3.common.shared.AddressCommandHandler;
import com.rew3.paymentoption.PaymentOptionCommandHandler;
import com.rew3.paymentterm.PaymentTermCommandHandler;
import com.rew3.sale.customer.CustomerCommandHandler;
import com.rew3.sale.invoice.InvoiceCommandHandler;
import com.rew3.sale.recurringinvoice.RecurringInvoiceCommandHandler;
import com.rew3.user.UserCommandHandler;

import javax.servlet.ServletContext;

public class Application {
	private static ServletContext context;

	/* Called by Listener */
	public static void setServletContext(ServletContext context) {
		Application.context = context;
		System.out.println("CONTEXT INIT");
		
		//Command Handler
		InvoiceCommandHandler.registerCommands();
		UserCommandHandler.registerCommands();
		

		ProductCommandHandler.registerCommands();
		AclCommandHandler.registerCommands();
		
		AccountCodeCommandHandler.registerCommands();
		JournalCommandHandler.registerCommands();
		AccountPeriodCommandHandler.registerCommands();
        PaymentTermCommandHandler.registerCommands();
        CustomerCommandHandler.registerCommands();
        AddressCommandHandler.registerCommands();
        PaymentOptionCommandHandler.registerCommands();
		//AttachmentCommandHandler.registerCommands();


		GcpCommandHandler.registerCommands();
		AcpCommandHandler.registerCommands();
		DeductionCommandHandler.registerCommands();
		AssociateCommandHandler.registerCommands();
		TransactionCommandHandler.registerCommands();

		//Event Handler
		SalesEventHandler.registerEvents();
		CommissionPlanCommandHandler.registerCommands();
		RecurringInvoiceCommandHandler.registerCommands();

	}

	/* Use this method to access context from any location */
	public static ServletContext getServletContext() {
		return Application.context;
		
	}
}

package com.rew3.billing.invoice.job;

import java.util.List;

import com.rew3.billing.invoice.InvoiceQueryHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rew3.billing.invoice.model.Invoice;
import com.rew3.common.utils.DateTime;

public class InvoiceRecurJob implements Job {
	public static boolean isRunning = false;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			if(isRunning) {
				System.out.println("Already Running Invoice Recur Job. Aborted New Execution Request.");
				return;
			}
			isRunning = true;
			
			
			System.out.println("Invoice Recur Job Started");
			System.out.println(DateTime.getCurrentTimestamp());

			InvoiceQueryHandler q = new InvoiceQueryHandler();
			List<Invoice> invoiceList = q.getRecurrableInvoices();
			
			for(Invoice i: invoiceList) {
//				ICommand c = new CreateRecurringInvoice(i);
//				CommandRegister.getInstance().process(c);
			}

			System.out.println(DateTime.getCurrentTimestamp());
			System.out.println("Invoice Recur Job Ended");
			
			isRunning = false;
		}
		catch (Exception ex) {
			isRunning = false;
			try {
				throw ex;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
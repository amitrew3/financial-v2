package com.rew3.accounting.accountingjournal.job;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.common.application.NotFoundException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rew3.sale.invoice.model.Invoice;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.utils.DateTime;
import com.rew3.accounting.accountingjournal.command.CreateInvoiceAccountingJournal;

import javax.servlet.ServletException;

public class InvoiceJournalJob implements Job {
	public static boolean isRunning = false;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			if(isRunning) {
				System.out.println("Already Running Invoice Journal Job. Aborted New Execution Request.");
				return;
			}
			isRunning = true;

			System.out.println("Invoice Journal Job Started");
			System.out.println(DateTime.getCurrentTimestamp());

			InvoiceQueryHandler q = new InvoiceQueryHandler();
			List<Invoice> invoiceList = q.getPendingJournalInvoices();

			for(Invoice i: invoiceList) {
				ICommand c = new CreateInvoiceAccountingJournal(i, true);
				CommandRegister.getInstance().process(c);
			}

			System.out.println(DateTime.getCurrentTimestamp());
			System.out.println("Invoice Journal Job Ended");


			isRunning = false;
		}
		catch (Exception ex) {
			isRunning = false;
			try {
				try {
					throw ex;
				} catch (NotFoundException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (ServletException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// try {
		// Mailer.send("jawaidgadiwala@hotmail.com", "CRON RAN", "Invoice Job
		// Ran Successfully");
		// } catch (AddressException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (MessagingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
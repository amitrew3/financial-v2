package com.rew3.billing.sales.job;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rew3.billing.invoice.command.CreateSalesInvoice;
import com.rew3.billing.sales.SalesQueryHandler;
import com.rew3.billing.sales.model.Sales;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.utils.DateTime;

import javax.servlet.ServletException;

public class SalesInvoiceJob implements Job {
	public static boolean isRunning = false;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			if(isRunning) {
				System.out.println("Already Running Sales Invoice Job. Aborted New Execution Request.");
				return;
			}
			isRunning = true;
			
			
			System.out.println("Sales Invoice Job Started");
			System.out.println(DateTime.getCurrentTimestamp());
			
			SalesQueryHandler q = new SalesQueryHandler();
			List<Sales> salesList = q.getInvoiceableSales();
			
			for(Sales s: salesList) {
				ICommand c = new CreateSalesInvoice(s);
				CommandRegister.getInstance().process(c);	
			}
			
			System.out.println(DateTime.getCurrentTimestamp());
			System.out.println("Sales Invoice Job Ended");
			
			
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
package com.rew3.accounting.accountingperiod.job;

import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.utils.DateTime;
import com.rew3.accounting.accountingperiod.AccountingPeriodQueryHandler;
import com.rew3.accounting.accountingperiod.command.CloseAccountingPeriod;
import com.rew3.accounting.accountingperiod.model.AccountingPeriod;

public class AccountingPeriodJob implements Job {
	public static boolean isRunning = false;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			if(isRunning) {
				System.out.println("Already Running Accounting Period Job. Aborted New Execution Request.");
				return;
			}
			isRunning = true;
			
			
			System.out.println("Accounting Period Job Started");
			System.out.println(DateTime.getCurrentTimestamp());
			
			AccountingPeriodQueryHandler q = new AccountingPeriodQueryHandler();
			List<AccountingPeriod> acpList = q.getCloseable();

			
			for(AccountingPeriod acp: acpList) {
				HashMap<String,Object> data= new HashMap<>();
				data.put("id",acp.get_id());
				ICommand c = new CloseAccountingPeriod(data);
				CommandRegister.getInstance().process(c);	
			}
			
			System.out.println(DateTime.getCurrentTimestamp());
			System.out.println("Accounting Period Job Ended");
			
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
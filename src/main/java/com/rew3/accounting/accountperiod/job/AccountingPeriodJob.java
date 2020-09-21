package com.rew3.accounting.accountperiod.job;

import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.utils.DateTime;
import com.rew3.accounting.accountperiod.AccountPeriodQueryHandler;
import com.rew3.accounting.accountperiod.command.CloseAccountingPeriod;
import com.rew3.accounting.accountperiod.model.AccountPeriod;

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
			
			AccountPeriodQueryHandler q = new AccountPeriodQueryHandler();
			List<AccountPeriod> acpList = q.getCloseable();

			
			for(AccountPeriod acp: acpList) {
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
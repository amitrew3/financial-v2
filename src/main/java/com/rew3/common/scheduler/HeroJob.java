package com.rew3.common.scheduler;

import com.rew3.common.cqrs.EventBus;
import com.rew3.common.cqrs.IEvent;
import com.rew3.common.utils.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HeroJob implements Job {
	private final int maxJobCount = 10;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {

			System.out.println("Event Job Started");
			System.out.println(DateTime.getCurrentTimestamp());

			if (EventBus.getInstance().getActiveProcessCount() < this.maxJobCount) {
				IEvent event = null;
				synchronized (this) {
					event = EventBus.getInstance().dequeue();
				}

				if (event != null) {
					EventBus.getInstance().process(event);
				}
			}
			else {
				System.out.println("Active Event Jobs Count Reached to MaxJobCount");
			}

			System.out.println(DateTime.getCurrentTimestamp());
			System.out.println("Event Job Ended");

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
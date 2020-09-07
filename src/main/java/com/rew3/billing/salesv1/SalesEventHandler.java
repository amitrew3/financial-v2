package com.rew3.billing.salesv1;

import com.rew3.billing.salesv1.event.SalesCreated;
import com.rew3.common.cqrs.EventStore;
import com.rew3.common.cqrs.IEvent;
import com.rew3.common.cqrs.IEventHandler;

public class SalesEventHandler implements IEventHandler {

	public static void registerEvents() {
		EventStore.getInstance().registerEventHandler(SalesCreated.class, SalesEventHandler.class);
	}

	public void handle(IEvent e) {
		if (e instanceof SalesCreated) {
			handle((SalesCreated) e);
		}
	}
	
	public void handle(SalesCreated e) {
		System.out.println("--SALES CREATED EVENT TRIGGERED--");
	}
}

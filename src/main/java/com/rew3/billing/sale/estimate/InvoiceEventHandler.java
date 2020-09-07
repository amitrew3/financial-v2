package com.rew3.billing.sale.estimate;

import com.rew3.billing.sale.estimate.event.InvoiceAccepted;
import com.rew3.billing.sale.estimate.event.InvoiceCreated;
import com.rew3.common.cqrs.EventStore;
import com.rew3.common.cqrs.IEvent;
import com.rew3.common.cqrs.IEventHandler;

public class InvoiceEventHandler implements IEventHandler {

	public static void registerEvents() {
		EventStore.getInstance().registerEventHandler(InvoiceCreated.class, InvoiceEventHandler.class);
		EventStore.getInstance().registerEventHandler(InvoiceAccepted.class, InvoiceEventHandler.class);
	}

	public void handle(IEvent e) {
		if (e instanceof InvoiceCreated) {
			handle((InvoiceCreated) e);
		} else if (e instanceof InvoiceAccepted) {
			handle((InvoiceAccepted) e);
		} 
	}
	
	public void handle(InvoiceCreated e) {
		System.out.println("--INVOICE CREATED EVENT TRIGGERED--");
	}
	
	public void handle(InvoiceAccepted e) {
		System.out.println("--INVOICE ACCEPTED EVENT TRIGGERED--");
	}
	
}

/*
package com.rew3.payment;

import com.rew3.payment.event.PaymentCreated;
import com.rew3.common.cqrs.EventStore;
import com.rew3.common.cqrs.IEvent;
import com.rew3.common.cqrs.IEventHandler;

public class PaymentEventHandler implements IEventHandler {

	public static void registerEvents() {
		EventStore.getInstance().registerEventHandler(PaymentCreated.class, PaymentEventHandler.class);
	}

	public void handle(IEvent e) {
		if (e instanceof PaymentCreated) {
			handle((PaymentCreated) e);
		}
	}
	
	public void handle(PaymentCreated e) {
		System.out.println("--PAYMENT CREATED EVENT TRIGGERED--");
	}
}
*/

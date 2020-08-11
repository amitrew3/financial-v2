package com.rew3.common.cqrs;

public interface IEventHandler {
	void handle(IEvent c);
}

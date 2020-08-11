package com.rew3.common.cqrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;

import javax.servlet.ServletException;

public interface ICommandHandler  {
	void handle(ICommand c ) throws Exception;
}

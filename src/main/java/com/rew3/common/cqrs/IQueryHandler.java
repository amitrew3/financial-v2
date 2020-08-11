package com.rew3.common.cqrs;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;

import java.util.List;

public interface IQueryHandler {
	Object getById(String id) throws CommandException, NotFoundException, NotFoundException;
	List<Object> get(Query q);
}

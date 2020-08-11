package com.rew3.common.application;

import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;

public class CommandException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CommandException() {
		
	}
	
	public CommandException(Exception ex) {
		super(ex.getMessage(), ex.getCause());
	}
	
	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommandException(Throwable cause) {
		super(cause);
	}
	
	public CommandException(String message) {
		super(message);
		APILogger.add(APILogType.ERROR, message);
	}
}

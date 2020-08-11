package com.rew3.common.application;

import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;

public class MethodNotAccepted extends Exception {

	private static final long serialVersionUID = 1L;

	public MethodNotAccepted() {

	}

	public MethodNotAccepted(Exception ex) {
		super(ex.getMessage(), ex.getCause());
	}

	public MethodNotAccepted(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodNotAccepted(Throwable cause) {
		super(cause);
	}

	public MethodNotAccepted(String message) {
		super(message);
		APILogger.add(APILogType.ERROR, message);
	}
}

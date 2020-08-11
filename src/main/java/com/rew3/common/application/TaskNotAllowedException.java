package com.rew3.common.application;

import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;

public class TaskNotAllowedException extends Exception {

    private static final long serialVersionUID = 1L;

    public TaskNotAllowedException() {

    }

    public TaskNotAllowedException(Exception ex) {
        super(ex.getMessage(), ex.getCause());
    }

    public TaskNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotAllowedException(Throwable cause) {
        super(cause);
    }

    public TaskNotAllowedException(String message) {
        super(message);
        APILogger.add(APILogType.ERROR, message);
    }
}

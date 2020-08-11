package com.rew3.common.application;

import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {

    }

    public NotFoundException(Exception ex) {
        super(ex.getMessage(), ex.getCause());
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message) {
        super(message);
        APILogger.add(APILogType.ERROR, message);
    }
}

package com.app.management.service.exceptions;

public class DuplicateInstanceException extends Exception {
    
    public DuplicateInstanceException() {
        super();
    }

    public DuplicateInstanceException(String message) {
        super(message);
    }

    public DuplicateInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    
}

package com.company.exception;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public TaskNotFoundException(String s) {
        super(s);
    }
}

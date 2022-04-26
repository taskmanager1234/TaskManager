package com.company.exception;

public class ValidationException extends Exception {

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ValidationException(String s) {
        super(s);
    }
}

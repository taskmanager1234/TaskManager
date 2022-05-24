package com.company.exception;

public class BadImportException extends Exception {

    public BadImportException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public BadImportException(String s) {
        super(s);
    }
}

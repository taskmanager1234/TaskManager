package com.company.exception;

public class UnexpectedFileExtensionException extends Exception {
    public UnexpectedFileExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public UnexpectedFileExtensionException(String s) {
        super(s);
    }
}

package com.company.exception;

public class BadCriterionException extends Exception {

    public BadCriterionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public BadCriterionException(String s) {
        super(s);
    }
}

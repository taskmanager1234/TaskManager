package com.company.exception;

public class DeleteTaskException extends Exception {

    public DeleteTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public DeleteTaskException(String s) {
        super(s);
    }
}

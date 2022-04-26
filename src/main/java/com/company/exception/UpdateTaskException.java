package com.company.exception;

public class UpdateTaskException extends Exception {

    public UpdateTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public UpdateTaskException(String s) {
        super(s);
    }
}

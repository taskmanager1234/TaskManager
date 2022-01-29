package com.company.exception;

public class CreateTaskException extends Exception {

    public CreateTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public CreateTaskException(String s) {
        super(s);
    }
}

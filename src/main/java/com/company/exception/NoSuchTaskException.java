package com.company.exception;

public class NoSuchTaskException extends Exception {

    public NoSuchTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public NoSuchTaskException(String s) {
        super(s);
    }
}

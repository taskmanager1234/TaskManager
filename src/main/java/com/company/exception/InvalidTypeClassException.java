package com.company.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class InvalidTypeClassException extends JsonProcessingException {

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public InvalidTypeClassException(String s) {
        super(s);
    }
}

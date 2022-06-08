package com.company.validator;


import lombok.Data;

@Data
public class ValidationException extends Exception {
    private final String errorMessage;

    public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }

}

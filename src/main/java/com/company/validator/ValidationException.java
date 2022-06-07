package com.company.validator;


import lombok.Data;
//TODO ValidationException должен быть наследником от Exception
@Data
public class ValidationException extends Exception {
    private final String errorMessage;

    public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString(){
        return errorMessage;
    }

}

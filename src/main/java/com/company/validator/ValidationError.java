package com.company.validator;


import lombok.Data;
//TODO ValidationException должен быть наследником от Exception
@Data
public class ValidationError {
    private final String errorMessage;

    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString(){
        return errorMessage;
    }

}

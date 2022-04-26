package com.company.validator;

public interface Validator {
    ValidationError validate(Object object) throws ClassCastException;
}

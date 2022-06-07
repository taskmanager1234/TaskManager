package com.company.validator;

public interface Validator<T> {
    void validate(T object) throws ValidationException;
}

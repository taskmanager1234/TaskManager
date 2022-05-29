package com.company.validator;

public interface ValidationManager<T> {
    ValidationReport validate(T object);

}

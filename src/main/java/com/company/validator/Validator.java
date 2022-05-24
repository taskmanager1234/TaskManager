package com.company.validator;

import com.company.exception.SerializationException;
import com.company.exception.TaskNotFoundException;
import com.company.exception.UnexpectedFileExtensionException;

import java.io.IOException;

public interface Validator {
    ValidationError validate(Object object) throws ClassCastException, UnexpectedFileExtensionException, IOException, SerializationException, TaskNotFoundException;
}

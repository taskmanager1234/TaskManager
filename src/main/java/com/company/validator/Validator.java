package com.company.validator;

import com.company.exception.SerializationException;
import com.company.exception.TaskNotFoundException;
import com.company.exception.UnexpectedFileExtensionException;
import com.company.model.Task;

import java.io.IOException;

public interface Validator<T> {
    ValidationError validate(T object) throws ClassCastException, UnexpectedFileExtensionException, IOException, SerializationException, TaskNotFoundException;
}

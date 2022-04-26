package com.company.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    private final List<String> errors = new ArrayList<>();

    public void addError(String error) {
        errors.add(error);
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public String getAllErrorsAsString() {
        StringBuilder errorsAsString = new StringBuilder();
        for (String error : errors)
            errorsAsString.append(error).append("\n");
        return errorsAsString.substring(0, errorsAsString.length() - 2);
    }
}

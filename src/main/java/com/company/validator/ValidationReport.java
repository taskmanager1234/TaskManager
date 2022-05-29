package com.company.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValidationReport {
    private final List<ValidationError> errors = new ArrayList<>();

    public void addError(ValidationError error) {
        errors.add(error);
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public String getAllErrorsAsString() {
        StringBuilder errorsAsString = new StringBuilder();
        for (ValidationError error : errors)
            errorsAsString.append(error).append("\n");
        return errorsAsString.substring(0, errorsAsString.length() - 2);
    }

    public void addErrorIfPresent(ValidationError validationError){
        if(Objects.nonNull(validationError)){
            addError(validationError);
        }
    }
}

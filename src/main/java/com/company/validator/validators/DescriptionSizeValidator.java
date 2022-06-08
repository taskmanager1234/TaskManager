package com.company.validator.validators;

import com.company.model.Task;
import com.company.validator.ValidationException;
import com.company.validator.Validator;

public class DescriptionSizeValidator implements Validator<Task> {
    private static final int DESCRIPTION_MAX_LENGTH = 500;

    @Override
    public void validate(Task task) throws ValidationException {
        int lengthDescription = task.getDescription().length();

        if (lengthDescription > DESCRIPTION_MAX_LENGTH) {
            String stringException = String.format("Incorrect string length: %d. The length must be less than %d",
                    lengthDescription, DESCRIPTION_MAX_LENGTH);
            throw new ValidationException(stringException);
        }
    }
}

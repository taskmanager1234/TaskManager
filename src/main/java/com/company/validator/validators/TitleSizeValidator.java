package com.company.validator.validators;

import com.company.model.Task;
import com.company.validator.ValidationException;
import com.company.validator.Validator;


public class TitleSizeValidator implements Validator<Task> {
    private static final int TITLE_MAX_LENGTH = 32;

    @Override
    public void validate(Task task) throws ValidationException{
        int lengthTitle = task.getTitle().length();

        if (lengthTitle > TITLE_MAX_LENGTH) {
            String stringException = String.format("Incorrect string length: %d. The length must be less than %d",
                    lengthTitle, TITLE_MAX_LENGTH);
            throw  new ValidationException(stringException);
        }
    }
}

package com.company.validator.validators;

import com.company.model.Task;
import com.company.validator.ValidationException;
import com.company.validator.Validator;

public class TitlePatternValidator implements Validator<Task> {

    @Override
    public void validate(Task task) throws ValidationException {
        String title = task.getTitle();
        String titlePattern = "[а-яА-ЯёЁa-zA-Z0-9]+[а-яА-ЯёЁa-zA-Z0-9 -]+";
        boolean isTitleValid = title.matches(titlePattern);
        if (!isTitleValid) {
            String stringException = String.format("Incorrect Title: %s. " +
                    "It must consist of alphabetic characters, " +
                    "numbers, spaces and dashes." +
                    " Must not start with spaces and dashes.", title);
            throw new ValidationException(stringException);
        }
    }
}

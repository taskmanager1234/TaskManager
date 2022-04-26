package com.company.validator.impl;

import com.company.model.Task;
import com.company.validator.ValidationError;
import com.company.validator.Validator;

public class TaskValidator implements Validator {

    public static final int MAX_TITLE_LENGTH = 32;
    public static final int MAX_DESCRIPTION_LENGTH = 500;

    public ValidationError validate(Object object) throws ClassCastException {
        ValidationError validationError = new ValidationError();
        String titlePattern = "[а-яА-ЯёЁa-zA-Z0-9]+[а-яА-ЯёЁa-zA-Z0-9 -]+";

        Task task = (Task) object;

        boolean isTitleValid = task.getTitle().matches(titlePattern);
        //TODO Механизм для валидации тасок, механизм должен позволять легко добавлять новые валидаторы
        //TODO на вход механизма валдиации подается таска, а на выходе либо ничего если все хорошо,
        // либо error со всеми ошибками валидациями
        if (!isTitleValid)
            validationError.addError(String.format("Incorrect Title: %s. " +
                    "It must consist of alphabetic characters, " +
                    "numbers, spaces and dashes." +
                    " Must not start with spaces and dashes.", task.getTitle()));
        if (task.getTitle().length() > MAX_TITLE_LENGTH)
            validationError.addError(String.format("Incorrect title length: %d. The length must be less than %d",
                    task.getTitle().length(), MAX_TITLE_LENGTH));

        if (task.getTitle().length() > MAX_DESCRIPTION_LENGTH)
            validationError.addError(String.format("Incorrect description length: %d. The length must be less than %d",
                    task.getDescription().length(), MAX_DESCRIPTION_LENGTH));

        if (task.getStartDate().isAfter(task.getEndDate()))
            validationError.addError("Start date must be before end date");

        return validationError;
    }
}

package com.company.validator.impl;

import com.company.model.Task;
import com.company.validator.*;
import com.company.validator.validators.DateValidator;
import com.company.validator.validators.DescriptionSizeValidator;
import com.company.validator.validators.TitleSizeValidator;
import com.company.validator.validators.TitlePatternValidator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TaskValidationManager implements ValidationManager<Task> {

    private static final List<Validator<Task>> VALIDATORS = Arrays.asList(
            new TitlePatternValidator(),
            new TitleSizeValidator(),
            new DescriptionSizeValidator(),
            new DateValidator()
    );
    public ValidationReport validate(Task task) {

        ValidationReport validationReport = new ValidationReport();

        for (Validator<Task> taskValidator: VALIDATORS) {
            try {
                taskValidator.validate(task);
            } catch (ValidationException validationException) {
                validationReport.addError(validationException);
            }
        }
        return validationReport;
    }
}

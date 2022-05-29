package com.company.validator.impl;

import com.company.model.Task;
import com.company.validator.*;
import com.company.validator.validators.DateValidator;
import com.company.validator.validators.StringSizeValidator;
import com.company.validator.validators.TitlePatternValidator;

import java.time.LocalDateTime;

public class TaskValidationManager implements ValidationManager<Task> {

    private static final int TITLE_MAX_LENGTH = 32;
    private static final int DESCRIPTION_MAX_LENGTH = 500;

    public ValidationReport validate(Task task) throws ClassCastException {

        ValidationReport validationReport = new ValidationReport();

        String title = task.getTitle();
        String description = task.getDescription();
        LocalDateTime startDate = task.getStartDate();
        LocalDateTime endDate = task.getEndDate();

        validationReport.addErrorIfPresent(DateValidator.validate(startDate, endDate));
        validationReport.addErrorIfPresent(StringSizeValidator.validate(description, DESCRIPTION_MAX_LENGTH));
        validationReport.addErrorIfPresent(StringSizeValidator.validate(title, TITLE_MAX_LENGTH));
        validationReport.addErrorIfPresent(TitlePatternValidator.validate(title));

        return validationReport;
    }
}

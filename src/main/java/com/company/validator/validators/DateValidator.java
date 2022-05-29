package com.company.validator.validators;

import com.company.validator.ValidationError;

import java.time.LocalDateTime;

public class DateValidator {

    public static ValidationError validate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate))
            return new ValidationError("Start date must be before end date");
        else return null;
    }
}

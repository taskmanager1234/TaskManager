package com.company.validator.validators;

import com.company.validator.ValidationError;

import java.time.LocalDateTime;

//TODO добавить интерфейс
//TODO не статик
public class DateValidator {

    public static ValidationError validate(LocalDateTime startDate, LocalDateTime endDate) {//TODO передаем Task (лучше T)
        if (startDate.isAfter(endDate))
            return new ValidationError("Start date must be before end date");
        else return null;
    }
}

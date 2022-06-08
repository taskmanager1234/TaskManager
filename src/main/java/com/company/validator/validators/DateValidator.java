package com.company.validator.validators;

import com.company.model.Task;
import com.company.validator.ValidationException;
import com.company.validator.Validator;

import java.time.LocalDateTime;

//TODO добавить интерфейс
//TODO не статик
public class DateValidator implements Validator<Task> {

    @Override
    public void validate(Task task) throws ValidationException {//TODO передаем Task
        LocalDateTime startDate = task.getStartDate();
        LocalDateTime endDate = task.getEndDate();
        if (startDate.isAfter(endDate))
            throw  new ValidationException("Start date must be before end date");
    }


}

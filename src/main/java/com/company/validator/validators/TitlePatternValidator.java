package com.company.validator.validators;

import com.company.validator.ValidationError;

public class TitlePatternValidator {

    public static ValidationError validate(String title) {
        String titlePattern = "[а-яА-ЯёЁa-zA-Z0-9]+[а-яА-ЯёЁa-zA-Z0-9 -]+";
        boolean isTitleValid = title.matches(titlePattern);
        if (!isTitleValid) {
            String stringException = String.format("Incorrect Title: %s. " +
                    "It must consist of alphabetic characters, " +
                    "numbers, spaces and dashes." +
                    " Must not start with spaces and dashes.", title);
            return new ValidationError(stringException);
        }
        return null;

    }
}

package com.company.validator.validators;

import com.company.validator.ValidationError;

public class StringSizeValidator {


    public static ValidationError validate(String string, int validLength) {
        if (string.length() > validLength) {
            String stringException = String.format("Incorrect string length: %d. The length must be less than %d",
                    string.length(), validLength);
            return new ValidationError(stringException);
        }
        return null;
    }
}

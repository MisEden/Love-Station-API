package org.eden.lovestation.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomDateValidator implements
        ConstraintValidator<CustomDateConstraint, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public void initialize(CustomDateConstraint customDate) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try {
            if (field == null) {
                return true;
            }
            sdf.parse(field);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

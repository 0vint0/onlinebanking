package com.vsvet.example.onlinebanking.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public abstract class AbstractConstraintValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        boolean isValid = isValid(value);
        if (!isValid) {
            context.buildConstraintViolationWithTemplate(getValidationMessage(value)).addConstraintViolation();
            context.disableDefaultConstraintViolation();
        }
        return isValid;
    }

    protected abstract boolean isValid(T value);

    /**
     * In future this can be replaced with constant(or error code),
     * to implement internationalization on UI.
     * @return
     */
    protected abstract String getValidationMessage(T value);
}

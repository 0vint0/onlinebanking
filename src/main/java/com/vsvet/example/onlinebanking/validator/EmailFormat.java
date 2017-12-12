package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.EmailFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailFormatValidator.class)
@Documented
public @interface EmailFormat {

    String message() default "Invalid email format!";

    String pattern() default "([a-zA-Z0-9_\\-\\+]+\\.?)+@([a-zA-Z0-9_-]+\\.)+[a-zA-Z]{2,}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

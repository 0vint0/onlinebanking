package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.NotNullOrBlankValidator;
import com.vsvet.example.onlinebanking.validator.impl.SameConfirmPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SameConfirmPasswordValidator.class)
@Documented
public @interface SameConfirmPassword {

    String message() default "Confirm password is different!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

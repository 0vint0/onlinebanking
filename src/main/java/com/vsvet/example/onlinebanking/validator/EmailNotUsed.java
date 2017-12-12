package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.EmailNotUsedValidator;
import com.vsvet.example.onlinebanking.validator.impl.NotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailNotUsedValidator.class)
@Documented
public @interface EmailNotUsed {

    String message() default "Email ''{0}'' is already in use!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

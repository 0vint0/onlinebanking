package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.ClientHasDefaultCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientHasDefaultCardValidator.class)
@Documented
public @interface ClientHasDefaultCard {

    String message() default "Client ''{0}'' has no default cards!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.ClientEmailExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientEmailExistValidator.class)
@Documented
public @interface ClientEmailExist {

    String message() default "Client with ''{0}'' email does not exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

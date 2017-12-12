package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.ActiveCardAccountExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ActiveCardAccountExistValidator.class)
@Documented
public @interface ActiveCardAccountExist {

    String message() default "Card account with ''{0}'' number does not exist for client ''{1}'' or is not active!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

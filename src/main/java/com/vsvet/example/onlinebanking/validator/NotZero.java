package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.NotZeroValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotZeroValidator.class)
@Documented
public @interface NotZero {

    String message() default "Field ''{0}'' must not be zero";

    String fieldName() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

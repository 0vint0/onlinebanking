package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.EmailFormatValidator;
import com.vsvet.example.onlinebanking.validator.impl.NotNullOrBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullOrBlankValidator.class)
@Documented
public @interface NotNullOrBlank {

    String message() default "Field ''{0}'' must not be blank";

    String fieldName() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

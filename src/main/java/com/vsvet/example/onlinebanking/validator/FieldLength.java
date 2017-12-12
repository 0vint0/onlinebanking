package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.FieldLengthValidator;
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
@Constraint(validatedBy = FieldLengthValidator.class)
@Documented
public @interface FieldLength {

    String message() default "Field ''{0}'' has invalid length, expected [min>={1}, max<{2}]!";

    String fieldName() default "";

    int minLength() default 6;

    int maxLength() default -1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

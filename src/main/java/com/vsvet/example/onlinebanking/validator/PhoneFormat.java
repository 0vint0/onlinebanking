package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.NotNullOrBlankValidator;
import com.vsvet.example.onlinebanking.validator.impl.PhoneFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneFormatValidator.class)
@Documented
public @interface PhoneFormat {

    String message() default "Invalid phone format ''{0}'' , expect to be {1} !";

    String pattern() default "^((0))[0-9]{9}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

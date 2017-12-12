package com.vsvet.example.onlinebanking.validator;

import com.vsvet.example.onlinebanking.validator.impl.ClientEmailExistValidator;
import com.vsvet.example.onlinebanking.validator.impl.ClientValidStatusValidator;
import com.vsvet.example.onlinebanking.view.ClientStatusView;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientValidStatusValidator.class)
@Documented
public @interface ClientValidStatus {

    String message() default "Invalid client ''{0}'' status, expected ''{1}''!";

    ClientStatusView status() default ClientStatusView.ACTIVE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

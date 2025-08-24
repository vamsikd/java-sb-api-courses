package com.example.webapp.application.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptySetValidator.class)
public @interface NotEmptySet {
    String message() default "Set cannot be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

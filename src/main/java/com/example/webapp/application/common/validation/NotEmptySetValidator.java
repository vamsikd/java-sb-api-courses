package com.example.webapp.application.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class NotEmptySetValidator implements ConstraintValidator<NotEmptySet, Set<?>> {

    @Override
    public boolean isValid(Set<?> value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty();
    }
}

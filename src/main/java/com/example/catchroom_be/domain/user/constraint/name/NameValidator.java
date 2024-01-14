package com.example.catchroom_be.domain.user.constraint.name;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName,String> {
    @Override
    public void initialize(ValidName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name != null && name.matches("^[a-zA-Z가-힣]*$");
    }
}

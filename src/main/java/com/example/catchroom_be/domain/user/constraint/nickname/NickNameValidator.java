package com.example.catchroom_be.domain.user.constraint.nickname;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NickNameValidator implements ConstraintValidator<ValidNickName,String> {
    @Override
    public void initialize(ValidNickName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nickName, ConstraintValidatorContext context) {
        return nickName != null && nickName.matches("^[a-zA-Z0-9가-힣]*$");
    }
}

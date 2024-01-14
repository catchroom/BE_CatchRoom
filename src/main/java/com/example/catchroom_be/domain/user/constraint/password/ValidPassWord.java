package com.example.catchroom_be.domain.user.constraint.password;

import com.slack.api.webhook.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassWord {
    String message() default "비밀번호 형식이 유효하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

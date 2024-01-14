package com.example.catchroom_be.domain.user.constraint.name;

import com.slack.api.webhook.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
public @interface ValidName {

    String message() default "이름 형식이 올바르지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

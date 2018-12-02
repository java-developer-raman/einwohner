package com.sharma.data.validator.annotation;

import com.sharma.data.validator.impl.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {
    String message() default "Email ist nicht richtig, erwartet wie xxx@xxx.xx";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

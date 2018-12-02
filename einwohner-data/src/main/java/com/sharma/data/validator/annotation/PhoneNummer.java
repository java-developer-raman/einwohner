package com.sharma.data.validator.annotation;

import com.sharma.data.validator.impl.PhoneNummerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNummerValidator.class)
public @interface PhoneNummer {
    String message() default "Phone nummmer ist nicht richtig, erwartet wie +<LandCode><PhoneNummer>";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

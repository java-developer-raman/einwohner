package com.sharma.data.validator.impl;

import com.sharma.data.validator.annotation.PhoneNummer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNummerValidator implements ConstraintValidator<PhoneNummer, String> {

    public static final Pattern VALID_PHONE_NUMBER_WITH_COUNTRY_CODE =
            Pattern.compile("^\\+[0-9]{2,3}[-]{1}[0-9]+", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(PhoneNummer constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = VALID_PHONE_NUMBER_WITH_COUNTRY_CODE.matcher(value);
        return matcher.find();
    }
}

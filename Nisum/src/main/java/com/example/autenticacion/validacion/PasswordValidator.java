package com.example.autenticacion.validacion;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${app.security.password.regex}")
    private String regex;

    @Value("${app.security.password.regex.message}")
    private String regexMessage;

    private Pattern pattern;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return false;

        boolean matches = pattern.matcher(value).matches();

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(regexMessage)
                    .addConstraintViolation();
        }

        return matches;
    }
}

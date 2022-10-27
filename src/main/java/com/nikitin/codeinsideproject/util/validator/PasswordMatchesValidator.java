package com.nikitin.codeinsideproject.util.validator;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.util.annonation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        PersonDto personDto = (PersonDto) obj;
        return personDto.getPassword().equals(personDto.getPasswordConfirm());
    }
}

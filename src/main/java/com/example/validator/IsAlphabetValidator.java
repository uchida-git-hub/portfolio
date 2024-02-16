package com.example.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsAlphabetValidator implements ConstraintValidator<IsAlphabet, String>{
	
	private static final String ISALPHABET_VALIDATION ="^[a-zA-Z\\d]{5,20}$";
	
	private static final Pattern ISALPHABET_PATTERN = Pattern.compile(ISALPHABET_VALIDATION);
	
	@Override
	public boolean isValid(String value , ConstraintValidatorContext context) {
		return ISALPHABET_PATTERN.matcher(value).matches();
	}
}

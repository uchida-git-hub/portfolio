package com.example.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid,String> {
	
	private static final String PASSWORD_VARIDATION = "^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z]).{10,20}$";
	
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_VARIDATION);
	
	@Override
	public boolean isValid(String value , ConstraintValidatorContext context) {
		return PASSWORD_PATTERN.matcher(value).matches();
	}
	
}

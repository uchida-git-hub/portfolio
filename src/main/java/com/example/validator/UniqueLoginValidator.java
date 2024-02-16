package com.example.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.AccountInfoMapper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String>{
	
	private final AccountInfoMapper accountInfoMapper;
	
	public UniqueLoginValidator() {
		this.accountInfoMapper = null;
	}
	
	@Autowired
	public UniqueLoginValidator(AccountInfoMapper accountInfoMapper) {
		this.accountInfoMapper = accountInfoMapper;
	}
	
	@Override
	public boolean isValid(String value , ConstraintValidatorContext context) {
		return accountInfoMapper == null || (accountInfoMapper.findByUserId(value) == null);
	}
	
}

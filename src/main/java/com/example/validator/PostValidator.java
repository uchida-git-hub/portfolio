package com.example.validator;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostValidator implements ConstraintValidator<PostVaild, String>{
	
	private final static List<String> NGWORDS;
	
	private final static String NGWORD;
	
	static {
		
		NGWORDS = new ArrayList<>();
		NGWORDS.add("バカ");
		NGWORDS.add("馬鹿");
		NGWORDS.add("ばか");
		NGWORDS.add("アホ");
		NGWORDS.add("無能");
		
		NGWORD = "String";
	};
	
	@Override
	public boolean isValid(String value , ConstraintValidatorContext context) {
		for(String ngword : NGWORDS) {
			if(value.contains(ngword)) {
				return false;
			}
		}
		return true;
	}
	

}

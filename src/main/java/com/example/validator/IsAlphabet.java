package com.example.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsAlphabetValidator.class)
public @interface IsAlphabet {

	String message() default"使用できない文字が含まれています";
	Class<?>[]groups()default{};
	Class<? extends Payload>[] payload() default{};
}

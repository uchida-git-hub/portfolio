package com.example.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.METHOD , ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {
	
	String message() default"パスワードは10文字以上20文字以下で1つ以上の大文字と小文字、数字を含む内容にしてください";
	Class<?>[]groups() default{};
	Class<? extends Payload>[] payload() default{};
}
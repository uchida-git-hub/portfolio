package com.example.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * ユーザーIDの登録の際の重複チェックのバリデーション
 *
 */

@Target({ElementType.METHOD , ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueLoginValidator.class)
public @interface UniqueLogin {
	
	String message() default "このユーザー名は既に登録されています";
	Class<?>[] groups()default{};
	Class<? extends Payload>[] payload() default{}; 

}

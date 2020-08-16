package com.luv2code.springboot.thymeleafdemo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy= PasswordValidFinder.class)
public @interface PasswordValid {

	String message() default "Password is not valid";
	 Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	
}

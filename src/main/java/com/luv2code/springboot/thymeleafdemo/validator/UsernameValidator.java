package com.luv2code.springboot.thymeleafdemo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springboot.thymeleafdemo.dao.UserDao;


public class UsernameValidator implements ConstraintValidator<Username, String> {

	@Autowired
	private UserDao repository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
			return value!= null && repository.findByUsername(value)==null;
}
	
	

}

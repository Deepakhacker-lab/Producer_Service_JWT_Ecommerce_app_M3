package com.luv2code.springboot.thymeleafdemo.service;

public class UserAlreadyExistException extends Exception {
	private static final long serialVersionUID=-1l;
	public UserAlreadyExistException(String error) {
		System.err.println(error);
		
	}
}

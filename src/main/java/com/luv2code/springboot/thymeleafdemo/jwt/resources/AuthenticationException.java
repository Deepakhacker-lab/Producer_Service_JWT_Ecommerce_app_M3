package com.luv2code.springboot.thymeleafdemo.jwt.resources;
@SuppressWarnings("serial")
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}


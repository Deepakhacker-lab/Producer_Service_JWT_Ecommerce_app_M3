package com.luv2code.springboot.thymeleafdemo.jwt.resources;

import org.springframework.context.ApplicationEvent;

import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;

public class VerificationCodePublisher extends ApplicationEvent {

	private static final long serialVersionUID = -4113549487933175429L;
	

	private final DAOUser user;
	
	public VerificationCodePublisher(DAOUser user) {
		super(user);
	this.user=user;
	}

	public DAOUser getUser() {
		return user;
	}

}

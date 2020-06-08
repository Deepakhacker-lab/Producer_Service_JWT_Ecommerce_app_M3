package com.luv2code.springboot.thymeleafdemo.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.luv2code.springboot.thymeleafdemo.jwt.resources.VerificationCodePublisher;

@Component
public class EmailVerificationService implements ApplicationListener<VerificationCodePublisher>{
	
	@Autowired
	private JavaMailSender mailsender;

	@Autowired
	private VerificationTokenService Token;
	@Override
	public void onApplicationEvent(VerificationCodePublisher event) {
		System.err.println("inside Email event");
		String email= event.getUser().getEmail();
		System.err.println("got email" +email );
		String VerificationCode= Token.VerificationToken(event);
		System.err.println("got token" +VerificationCode );
		
		
		SimpleMailMessage mail= new SimpleMailMessage();
		
		mail.setSubject("Verification code for your new Account opening with Producer!");
		mail.setText("Account Code"+VerificationCode );
		mail.setTo(email);
		
		mailsender.send(mail);
		
		System.err.println("Mail has been send to user");
		
	}


}

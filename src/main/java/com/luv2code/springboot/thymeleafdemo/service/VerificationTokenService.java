package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.VerificationTokenRepository;
import com.luv2code.springboot.thymeleafdemo.entity.verificationCode;
import com.luv2code.springboot.thymeleafdemo.jwt.resources.VerificationCodePublisher;

@Service
public class VerificationTokenService {
	
	@Autowired
	private VerificationTokenRepository verify;
	
	public String VerificationToken(VerificationCodePublisher event) {
		
		
		
		List<verificationCode> verificationTokens = verify.
				findByUserEmail(event.getUser().getEmail());
        verificationCode verificationToken = new verificationCode();
            verificationToken.setUser(event.getUser());
             verify.save(verificationToken);
        return verificationToken.getToken();
		
	}

}

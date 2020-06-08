package com.luv2code.springboot.thymeleafdemo.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.verificationCode;

@Repository
public interface VerificationTokenRepository extends JpaRepository<verificationCode, String> {
	
    List<verificationCode> findByUserEmail(String email);
    List<verificationCode> findByToken(String token);
}
package com.luv2code.springboot.thymeleafdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;

@Repository
public interface UserDao extends JpaRepository<DAOUser, String> {

	List<DAOUser> findByEmail(String email);

	DAOUser findByUsername(String username);

	public void deleteById(Long id);

	

	

}
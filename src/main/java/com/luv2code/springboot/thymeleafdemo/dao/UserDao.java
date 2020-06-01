package com.luv2code.springboot.thymeleafdemo.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {

	com.luv2code.springboot.thymeleafdemo.entity.DAOUser findByUsername(String username);

	Optional<DAOUser> findById(Long id);
	
	
}
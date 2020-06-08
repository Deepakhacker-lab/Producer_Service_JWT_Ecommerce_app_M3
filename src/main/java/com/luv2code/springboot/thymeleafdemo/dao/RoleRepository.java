package com.luv2code.springboot.thymeleafdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.Role;

@Repository
public interface RoleRepository  extends JpaRepository<Role, String>{

		Role findByName(String name);

	}




package com.luv2code.springboot.thymeleafdemo.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	public long getId() {
		return id;
	}

	@Column
	private String username;
	@Column
	@JsonIgnore
	private String password;
	
	@Column
	private String Email;
	
	@Column
	private String role;
	
	@Column
	private int enabled;

	public int isEnabled() {
		return enabled;
	}

	public void setEnabled(int i) {
		this.enabled = i;
	}

	public String getEmail() {
		return Email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
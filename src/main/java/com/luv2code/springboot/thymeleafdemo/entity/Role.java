package com.luv2code.springboot.thymeleafdemo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	


	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "role_id")
	private DAOUser user_role;
	
	public DAOUser getUser() {
		return user_role;
	}

	public void setUser(DAOUser user_role) {
		this.user_role = user_role;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String role) {
		this.name = role;
	}
	
	
	public Role(String name) {
		this.name=name;
		
		
	}
	public Role() {}
}

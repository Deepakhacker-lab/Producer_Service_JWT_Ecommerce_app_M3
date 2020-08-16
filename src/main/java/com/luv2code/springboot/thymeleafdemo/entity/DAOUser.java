package com.luv2code.springboot.thymeleafdemo.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.luv2code.springboot.thymeleafdemo.validator.Username;


@Entity
@Table(name = "user")
public class DAOUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	public long getId() {
		return id;
	}

	@Column
	private String username;
	
	@Column
	
	private String password;
	
	@Column
	private String email;

	@Column
	private boolean enabled;
	
	@Column
	private boolean accountNonExpired;
	@Column
	private boolean credentialsNonExpired;
	@Column
	private boolean accountNonLocked;
	
	
	@OneToOne(mappedBy = "user_role", cascade=CascadeType.ALL)
    private Role role;
	
	public Role getRole() {
		return  role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

	public DAOUser() {
		
		this.enabled = false;
		this.accountNonExpired = true;
		this.credentialsNonExpired = true;
		this.accountNonLocked = true;
	}


	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	    private verificationCode verificationCode;
	 
	 public verificationCode getVerificationToken() {
	        return verificationCode;
	    }

	    public void setVerificationToken(verificationCode verificationToken) {
	        this.verificationCode = verificationToken;
	    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean i) {
		this.enabled = i;
	}

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
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
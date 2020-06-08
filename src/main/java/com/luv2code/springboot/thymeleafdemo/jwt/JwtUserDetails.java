package com.luv2code.springboot.thymeleafdemo.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


public  class JwtUserDetails implements UserDetails {

  private static final long serialVersionUID = 5155720064139820502L;

  private final Long id;
  private final String username;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean AccountNonLocked;
  private  final boolean Enabled;
  private final boolean AccountNonExpired;
  private final boolean CredentialsNonExpired;

  /*public JwtUserDetails(Long id, String username, String password, String role) {
    this.id = id;
    this.username = username;
    this.password = password;

    List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(role));

    this.authorities = authorities;
  }*/
  

  @JsonIgnore
  public Long getId() {
    return id;
  }

  public JwtUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities,
		boolean accountNonLocked, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired) {
	super();
	this.id = id;
	this.username = username;
	this.password = password;
	this.authorities = authorities;
	this.AccountNonLocked = accountNonLocked;
	this.Enabled = enabled;
	this.AccountNonExpired = accountNonExpired;
	this.CredentialsNonExpired = credentialsNonExpired;
}

@Override
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}



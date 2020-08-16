package com.luv2code.springboot.thymeleafdemo.jwt.resources;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtTokenResponse implements Serializable {

  private static final long serialVersionUID = 8317676219297719109L;

  private final String token;
  
  private final Collection<? extends GrantedAuthority> role;

    public JwtTokenResponse(String token, Collection<? extends GrantedAuthority> collection) {
        this.token = token;
        this.role=collection;
    }

    public String getToken() {
        return this.token;
    }
    public Collection<? extends GrantedAuthority> getRole() {
        return this.role;
    }
    
}
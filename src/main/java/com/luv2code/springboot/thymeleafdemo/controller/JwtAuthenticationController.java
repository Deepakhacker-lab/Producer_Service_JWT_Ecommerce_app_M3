package com.luv2code.springboot.thymeleafdemo.controller;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;
import com.luv2code.springboot.thymeleafdemo.entity.UserDTO;
import com.luv2code.springboot.thymeleafdemo.entity.verificationCode;
import com.luv2code.springboot.thymeleafdemo.jwt.JwtTokenUtil;
import com.luv2code.springboot.thymeleafdemo.jwt.resources.JwtTokenRequest;
import com.luv2code.springboot.thymeleafdemo.jwt.resources.JwtTokenResponse;
import com.luv2code.springboot.thymeleafdemo.service.JwtUserDetailsService;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	
	

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<URI> saveUser(@RequestBody UserDTO user) throws Exception {
		System.err.println("Inside save user ");
		
		DAOUser Created =userDetailsService.save(user);
		
		
		
		URI url= ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}/verifaction/{email}").buildAndExpand(Created.getId(),Created.getEmail())
				.toUri();
				
		System.err.println(url);
		return ResponseEntity.ok().body(url);
		
//		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	//http://localhost:8080/register/7/verifaction/vsdvdfv
	@RequestMapping(value = "/register/{id}/verifaction/{email}", method = RequestMethod.POST)
	public ResponseEntity<String> verification(@PathVariable Long id, @PathVariable String email,
			@RequestBody verificationCode user) throws Exception {
		
		
			boolean code = userDetailsService.verify(id, email, user);
			
			if(code==true) {
		 return new ResponseEntity<>(HttpStatus.OK);
			}
			else {
				return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
//		return ResponseEntity.ok(userDetailsService.save(user));
		
	}

		
}
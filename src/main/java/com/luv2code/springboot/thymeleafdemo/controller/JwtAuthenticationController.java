package com.luv2code.springboot.thymeleafdemo.controller;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;
import com.luv2code.springboot.thymeleafdemo.entity.UserDTO;
import com.luv2code.springboot.thymeleafdemo.entity.VerificationToken;
import com.luv2code.springboot.thymeleafdemo.jwt.JwtTokenUtil;
import com.luv2code.springboot.thymeleafdemo.jwt.resources.JwtTokenRequest;
import com.luv2code.springboot.thymeleafdemo.jwt.resources.JwtTokenResponse;
import com.luv2code.springboot.thymeleafdemo.jwt.resources.VerificationCodePublisher;
import com.luv2code.springboot.thymeleafdemo.service.JwtUserDetailsService;

@ControllerAdvice
@RestController
@CrossOrigin
public class JwtAuthenticationController {



	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private ApplicationEventPublisher publish;	

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) throws Exception {

		userDetailsService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token));
		
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<URI> saveUser(@RequestBody UserDTO user) throws Exception {
		System.err.println("Inside save user ");
		
		DAOUser Created =userDetailsService.save(user);
		
		
		publish.publishEvent(new VerificationCodePublisher(Created));
		URI url= ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}/verifaction/{email}").buildAndExpand(Created.getId(),Created.getEmail())
				.toUri();
				
		System.err.println(url);
		return ResponseEntity.ok().body(url);
		
//		return ResponseEntity.ok(userDetailsService.save(user));
	}

	
	//http://localhost:8080/register/7/verifaction/vsdvdfv
	@RequestMapping(value = "/register/{id}/verifaction/{email}", method = RequestMethod.POST)
	public ResponseEntity<String> verification(@PathVariable Long id, @PathVariable String email,
			@RequestBody VerificationToken user) throws Exception {
		
		System.err.println("Inside controller method "+ user.getToken());
		
			return userDetailsService.verify(id, email, user);
			
		}
//		return ResponseEntity.ok(userDetailsService.save(user));
	
	@DeleteMapping(("/login/{id}"))
	public ResponseEntity<Void>  Delete(@PathVariable Long id){
		userDetailsService.deleteById(id);
		 return ResponseEntity.noContent().build();
		
//		return ResponseEntity.ok(userDetailsService.save(user));
	}
		
	}

		

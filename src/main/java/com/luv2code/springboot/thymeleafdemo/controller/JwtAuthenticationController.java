package com.luv2code.springboot.thymeleafdemo.controller;
import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
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




@RestController
@CrossOrigin("http://192.168.99.100")
public class JwtAuthenticationController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

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

		return ResponseEntity.ok(new JwtTokenResponse(token, userDetails.getAuthorities()));
		
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<URI> saveUser(@Valid @RequestBody UserDTO user, BindingResult result) throws Exception {
		System.err.println("Inside save user ");
		
		if(result.hasErrors()) {
			System.err.println("Counts of the Errors are "+ result.getErrorCount());
			System.err.println("Object name " + result.getObjectName());
			System.err.println("field name " + result.getFieldError());
			return ResponseEntity.badRequest().body(null);
		}
		
		DAOUser Created =userDetailsService.save(user);
		
		
		publish.publishEvent(new VerificationCodePublisher(Created));
		URI url= ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}/verifaction/{email}").buildAndExpand(Created.getId(),Created.getEmail())
				.toUri();
				
		logger.debug("url has been generated successfully"+url);
		return ResponseEntity.ok().body(url);
		
//		return ResponseEntity.ok(userDetailsService.save(user));
	}

	
	//http://localhost:8080/register/7/verifaction/vsdvdfv
	@RequestMapping(value = "/register/{id}/verifaction/{email}", method = RequestMethod.POST)
	public ResponseEntity<String> verification(@PathVariable Long id, @PathVariable String email,
			@RequestBody VerificationToken user) throws Exception {
		
		logger.info("Inside controller method "+ user.getToken());
		
			return userDetailsService.verify(id, email, user);
			
		}
//		return ResponseEntity.ok(userDetailsService.save(user));
	
	@DeleteMapping(("/login/{id}"))
	public ResponseEntity<?>  Delete(@PathVariable Long id){
		
		if(userDetailsService.deleteById(id)) {
			
			logger.info("USer deleted "+ id);
			return new ResponseEntity<>("Successfully deleted ", HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>("Failed to delete ", HttpStatus.NOT_ACCEPTABLE);
		}
		
//		return ResponseEntity.ok(userDetailsService.save(user));
	}
	
}

		

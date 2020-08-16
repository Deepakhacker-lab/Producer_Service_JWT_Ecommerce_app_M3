package com.luv2code.springboot.thymeleafdemo.service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.RoleRepository;
import com.luv2code.springboot.thymeleafdemo.dao.UserDao;
import com.luv2code.springboot.thymeleafdemo.dao.VerificationTokenRepository;
import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;
import com.luv2code.springboot.thymeleafdemo.entity.Role;
import com.luv2code.springboot.thymeleafdemo.entity.UserDTO;
import com.luv2code.springboot.thymeleafdemo.entity.VerificationToken;
import com.luv2code.springboot.thymeleafdemo.entity.verificationCode;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private VerificationTokenRepository verify;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public void authenticate(String username, String password) throws Exception {
		try {
			DAOUser user = userDao.findByUsername(username);
			if(user!=null) {
				Role roles =  user.getRole();
		        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		            authorities.add(new SimpleGrantedAuthority(roles.getname()));
		            authorities.stream().filter(x->x!=null).forEach(x->System.err.println(x));
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password,authorities));
			}
			else {
				
				logger.error("User not found in database");
				throw new UsernameNotFoundException("User not found");
			}
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
		
		
		DAOUser user = userDao.findByUsername(username);
		if(user!=null) {
			Role roles =  user.getRole();
	        

	        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	            authorities.add(new SimpleGrantedAuthority(roles.getname()));
	            logger.info("User  found in database");
			return new org.springframework.security.core.userdetails.User(user.getUsername(),
					user.getPassword(),
					user.isEnabled(),
					user.isAccountNonExpired(), 
					user.isAccountNonLocked(),
					user.isCredentialsNonExpired(),
					authorities
					);
		
		}
		else {
			logger.error("User not found in database");
			throw new UsernameNotFoundException("User not found");
		}
	
	}
	

	public DAOUser save(UserDTO user) throws UserAlreadyExistException {
		String email= user.getEmail();
		List<DAOUser> users=userDao.findByEmail(email);
		
		if(users.isEmpty()) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		Role newrole= new Role(); 
		newrole.setname(user.getRole());
		newrole.setUser(newUser);
		rolerepo.save(newrole);
		newUser.setRole(newrole);
		
        DAOUser saved =userDao.save(newUser);
        
		System.err.println("User Saved"+saved);
		return saved;
		}
		else {
			 throw new UserAlreadyExistException("Email already exist: " + email);
		}
		
	}

	

	public ResponseEntity<String> verify(Long id, String email, VerificationToken user) {
		// TODO Auto-generated method stub
		String code= user.getToken();
		List<verificationCode> verifyToken= verify.findByToken(code);
		if (verifyToken.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		verificationCode verificationToken = verifyToken.get(0);
		if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
		verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setStatus(verificationCode.STATUS_VERIFIED);
        verificationToken.getUser().setEnabled(true);
        verify.save(verificationToken);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
		
		
		
	}

	@Transactional
	public boolean deleteById(Long id) {
		
		
		return userDao.deleteById(id);
		
		
	}
}
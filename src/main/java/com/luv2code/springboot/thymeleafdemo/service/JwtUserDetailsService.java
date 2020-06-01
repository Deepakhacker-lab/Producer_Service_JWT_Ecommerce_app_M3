package com.luv2code.springboot.thymeleafdemo.service;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.UserDao;
import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;
import com.luv2code.springboot.thymeleafdemo.entity.UserDTO;
import com.luv2code.springboot.thymeleafdemo.entity.verificationCode;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	
	 verificationCode verify = new verificationCode() ;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		DAOUser user = userDao.findByUsername(username);
		if (user.isEnabled() == 0) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public DAOUser save(UserDTO user) {
		if(user.getEmail()!=null) {
			String random= getRandomNumberString();
			verify.setCode(random);
			System.err.println(random);
		}
		
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		newUser.setRole(user.getRole());
		
		
		return userDao.save(newUser);
		
		
	}

	public  String getRandomNumberString() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}

	public boolean verify(Long id, String email, verificationCode user2) {
		// TODO Auto-generated method stub
		if(verify.getCode().equalsIgnoreCase(user2.getCode())) {
			System.err.println("verified");
			java.util.Optional<DAOUser> user = userDao.findById(id);
			System.err.println(user);
			if (user.isPresent()){
				DAOUser foo = user.get();
			   foo.setEnabled(1);
			   userDao.save(foo);
			   System.err.println(foo.isEnabled());
			   return true;
			}
			else{
				return false;
			}
	
	}
		else {
			return false;
		}
	}
}
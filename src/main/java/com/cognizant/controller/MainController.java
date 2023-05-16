package com.cognizant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.cognizant.config.JwtTokenUtil;
import com.cognizant.model.DAOUser;
import com.cognizant.model.JwtRequest;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.JwtUserDetailsService;


@Controller
@RequestMapping(path="/auth")
public class MainController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired 
	private UserRepository userRepository;
	
	
	
	boolean alreadyUser = false;
	DAOUser s = new DAOUser();

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path="/register")
	public ResponseEntity<?> addNewUser (@RequestBody DAOUser user )
			 {
		alreadyUser = false;
		
		userRepository.findAll().forEach( (e)->{
			System.out.print(e.getUsername());
			if(e.getUsername().equals(user.getUsername()) ) {
				System.out.print("I am Evaluated");
				alreadyUser = true;
			}
		});
		
		if(!alreadyUser) {
			System.out.print("I am creating user");
			System.out.print(user.toString());
			DAOUser newUser = userDetailsService.save(user);
			return ResponseEntity.ok("Hi "+newUser.getName()+",Your account is created successfully");
		}else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User already Exist Please login");
		}
		
		
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path="/login")
	public ResponseEntity<?> getAllUsers(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<JwtRequest>> violations = validator.validate(authenticationRequest);
		List<String> errors = new ArrayList<String>();
		for(ConstraintViolation<JwtRequest> violation : violations)
			errors.add(violation.getMessage());
		if(violations.size() > 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errors.toString());
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		userRepository.findAll().forEach( (e)->{
			
			if(e.getUsername().equals(authenticationRequest.getUsername()) ) {
				s = e;
			}
		});

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		System.out.print("token"+ token.isBlank()+ token.length());
//		if(token.) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password does not match");
//		}else {
		HashMap<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("id", s.getId());
		map.put("message", "Success");
		map.put("status", 200);
			return ResponseEntity.ok(map);
		//}

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
	
	
	
}

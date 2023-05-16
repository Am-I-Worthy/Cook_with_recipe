package com.cognizant.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.cognizant.service.JwtUserDetailsService;


import com.cognizant.config.JwtTokenUtil;
import com.cognizant.model.JwtRequest;
import com.cognizant.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		System.out.print("token"+ token.isBlank()+ token.length());
//		if(token.) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password does not match");
//		}else {
			return ResponseEntity.ok(new JwtResponse(token));
		//}
		
	}
	
//	@PostMapping("/register")
//	public ResponseEntity<?> saveUser(@RequestBody DAOUser user) throws Exception {
//		
//		System.out.print(user.toString());
//		return ResponseEntity.ok(userDetailsService.save(user));
//	}

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
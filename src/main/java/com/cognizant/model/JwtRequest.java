package com.cognizant.model;

import java.io.Serializable;

import javax.validation.constraints.*;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	@NotNull
	@Size(min = 2, message="Email not present")
	private String username;
	
	@NotNull
	@Size(min = 6,message="Password should be atleast 6 character")
	private String password;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
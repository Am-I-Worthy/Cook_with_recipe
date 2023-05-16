package com.cognizant.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@Column
	@NotNull
	@Size(min = 2)
	private String username;
	
	@Column
	@NotNull
	@Size(min = 6)
	private String password;
	
	@Column
	@NotNull
	@Size(min = 3)
	private String name;
	
	@Column
	@NotNull
	private String gender;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

	public String getId() {
		return  ""+id;
	}



	@Override
	public String toString() {
		return "DAOUser [username=" + username + ", name=" + name + ", gender=" + gender + ", password=" + password
				+ "]";
	}
	
	

}
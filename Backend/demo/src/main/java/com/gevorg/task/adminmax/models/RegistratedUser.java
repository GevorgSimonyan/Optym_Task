package com.gevorg.task.adminmax.models;


import io.swagger.annotations.ApiModelProperty;

public class RegistratedUser {

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String userName;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@ApiModelProperty(hidden = true)
	private String role = "user";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}

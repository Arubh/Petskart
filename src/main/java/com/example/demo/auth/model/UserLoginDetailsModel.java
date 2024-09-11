package com.example.demo.auth.model;

import java.util.Set;

public class UserLoginDetailsModel {
	private int userId;
	private String userName;
	private String email;
	private Set<String> roles;

	public UserLoginDetailsModel(int userId, String userName, String email, Set<String> roles) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.roles = roles;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public UserLoginDetailsModel() {
		super();
	}

	

	
}
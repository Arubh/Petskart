package com.example.demo.auth.model;

public class JwtRequestModel {
	String email;
	String password;

	public JwtRequestModel(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public JwtRequestModel() {
	}

	
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
}
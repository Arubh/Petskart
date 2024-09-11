package com.example.demo.auth.model;

import org.springframework.stereotype.Service;

public class APILoginReturnModel {
	private String status;
	private String message;
	private String token;
	private UserLoginDetailsModel user;

	public APILoginReturnModel(String status, String message, String token, UserLoginDetailsModel user) {
		super();
		this.status = status;
		this.message = message;
		this.token = token;
		this.user = user;

	}

	public APILoginReturnModel() {
		super();
	}

	public UserLoginDetailsModel getUser() {
		return user;
	}

	public void setUser(UserLoginDetailsModel user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
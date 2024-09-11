package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.auth.model.UserModel;
import com.example.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserModel user = userRepo.findByEmail(email);
        if(user == null)throw new UsernameNotFoundException("user not found " + email);
        return new User(user.getEmail(),user.getPassword(),user.getAuthorities());
	}
	
	public UserService userService() {
		return this;
	}

	public UserModel getByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}

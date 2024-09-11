package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.auth.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Integer>{
	UserModel findByEmail (String email);
	Boolean existsByUserName(String username);
	Boolean existsByEmail(String email);
}

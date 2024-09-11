package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.PetsModel;

public interface PetsRepository extends JpaRepository<PetsModel,Integer>{
	
}

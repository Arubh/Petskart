package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.PetsImage;
import com.example.demo.models.PetsModel;

public interface PetsImageRepository extends JpaRepository<PetsImage,Integer>{

}

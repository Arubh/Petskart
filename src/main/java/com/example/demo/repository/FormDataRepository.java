package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.FormData;

public interface FormDataRepository extends JpaRepository<FormData, Long> {
    // Other methods if needed
//    FormData saveFormData(FormData formData);
}
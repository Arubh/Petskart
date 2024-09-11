package com.example.demo.service;

import java.util.List;

import com.example.demo.models.PetsModel;

public interface PetService {
	public List<PetsModel> getAllPets();
    
    public PetsModel createPet(PetsModel petsModel);    

    public PetsModel getPetById(int petId);

    public void deletePetById(int petId);
    
    public PetsModel updatePet(PetsModel petsModel);
}

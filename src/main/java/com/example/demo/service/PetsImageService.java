package com.example.demo.service;

import java.util.List;

import com.example.demo.models.PetsImage;


public interface PetsImageService {
	public List<PetsImage> getAllPetImg();
    
    public PetsImage createPetImg(PetsImage PetImage);    

    public PetsImage getPetImgById(int PetImgId);

    public void deletePetImgById(int PetImgId);
    
    public PetsImage updatePetImg(PetsImage PetImage);
   
}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.PetsImage;
import com.example.demo.models.PetsModel;
import com.example.demo.models.ProductImage;
import com.example.demo.models.ProductModel;
import com.example.demo.repository.PetsRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.FileUtil;

@Service
public class PetServiceImpl implements PetService {
	@Autowired
	private PetsRepository petsRepository;
	
	@Override
	public List<PetsModel> getAllPets() {
		List<PetsModel> lis = this.petsRepository.findAll();
		for(PetsModel x: lis) {
			PetsImage img = x.getpetsImg();
			if(img == null)continue;
			img.setImgData(FileUtil.decompressFile(img.getImgData()));
			x.setpetsImg(img);
		}
		return lis;
	}

	@Override
	public PetsModel createPet(PetsModel petsModel) {
		return this.petsRepository.save(petsModel);
	}

	@Override
	public PetsModel getPetById(int petId) {
		PetsModel model = this.petsRepository.findById(petId).get();
		PetsImage img = model.getpetsImg();
		img.setImgData(FileUtil.decompressFile(img.getImgData()));
		model.setpetsImg(img);
		return model;
	}

	@Override
	public void deletePetById(int petId) {
		PetsModel pet = this.petsRepository.findById(petId).get();
        this.petsRepository.delete(pet);
	}

	@Override
	public PetsModel updatePet(PetsModel petsModel) {
		if (petsModel != null) {
        	return this.petsRepository.save(petsModel);
        } else {
            return null;
        }
	}
}

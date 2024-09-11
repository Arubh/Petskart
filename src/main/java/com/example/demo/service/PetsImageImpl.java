package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.PetsImage;
import com.example.demo.repository.PetsImageRepository;
import com.example.demo.util.FileUtil;

@Service
public class PetsImageImpl implements PetsImageService{
	@Autowired
	private PetsImageRepository petsRepository;
	

	@Override
	public List<PetsImage> getAllPetImg() {
		List<PetsImage> lis = this.petsRepository.findAll();
		for(PetsImage x: lis) {
			x.setImgData(FileUtil.decompressFile(x.getImgData()));
		}
		return lis;
	}

	@Override
	public PetsImage createPetImg(PetsImage petImage) {
		petImage.setImgData(FileUtil.compressFile(petImage.getImgData()));
		return this.petsRepository.save(petImage);
	
	}

	@Override
	public PetsImage getPetImgById(int petImgId) {
		PetsImage petImage = this.petsRepository.findById(petImgId).get();
		petImage.setImgData(FileUtil.decompressFile(petImage.getImgData()));
		return petImage;
	}

	@Override
	public void deletePetImgById(int petImgId) {
		this.petsRepository.deleteById(petImgId);
			
	}

	@Override
	public PetsImage updatePetImg(PetsImage petImage) {
		if(petImage != null) {
			petImage.setImgData(FileUtil.compressFile(petImage.getImgData()));
			return this.petsRepository.save(petImage);
			
		}else return null;
	}

}

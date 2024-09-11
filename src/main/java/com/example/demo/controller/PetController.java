package com.example.demo.controller;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.apiresponsemodel.APIResponseModel;
import com.example.demo.models.PetsImage;
import com.example.demo.models.PetsModel;
import com.example.demo.models.ProductImage;
import com.example.demo.models.ProductModel;
import com.example.demo.service.PetServiceImpl;
import com.example.demo.service.PetsImageImpl;
import com.example.demo.service.ProductImageImpl;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/pets")
@CrossOrigin("*")

public class PetController {
		@Autowired
		private PetServiceImpl petService;
		@Autowired
		private PetsImageImpl petImgService;
		
		private APIResponseModel responseModel;
		
		private Vector<PetsModel> petsVec;

		@GetMapping("/")
		public ResponseEntity<?> getAllPets(){
			responseModel = new APIResponseModel();
			try {
	            List<PetsModel> allPets = this.petService.getAllPets();
	            petsVec = new Vector<>(allPets);
	            responseModel.setData(petsVec);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Got all pets");
	            responseModel.setCount(petsVec.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	        }
			return ResponseEntity.ok(responseModel);
		}
		@GetMapping("/{petId}/img")
		public ResponseEntity<?> getPetsImageById(@PathVariable("petId") int petId){
			responseModel = new APIResponseModel();
			try {
				petsVec = new Vector<>();
				PetsModel pet = this.petService.getPetById(petId);
	            if (pet != null)
	                petsVec.add(pet);
	            else
	                throw new Exception("Image Not found");
	            
	            responseModel.setData(petsVec);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Foung Image");
	            responseModel.setCount(petsVec.size());
	            byte[] imageData=pet.getpetsImg().getImgData();
				return ResponseEntity.status(HttpStatus.OK)
						.contentType(MediaType.valueOf(pet.getpetsImg().getImgType()))
						.body(imageData);
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	        }
			return ResponseEntity.ok(responseModel);
		}
		@GetMapping("/{petId}")
		public ResponseEntity<?> getPetsById(@PathVariable("petId") int petId){
			responseModel = new APIResponseModel();
			try {
				petsVec = new Vector<>();
				PetsModel pet = this.petService.getPetById(petId);
	            if (pet != null)
	                petsVec.add(pet);
	            else
	                throw new Exception("User Not found");
	            
	            responseModel.setData(petsVec);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Found Pet");
	            responseModel.setCount(petsVec.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	        }
			return ResponseEntity.ok(responseModel);
		}
		
		@DeleteMapping("/{petId}")
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		public ResponseEntity<?> deletePetById(@PathVariable int petId) {
		        responseModel = new APIResponseModel();
		        petsVec = new Vector<>();
		        try {
		            this.petService.deletePetById(petId);
		            responseModel.setData(petsVec);
		            responseModel.setStatus("Successful");
		            responseModel.setMessage("Deleted successfully");
		            responseModel.setCount(petsVec.size());
		        } catch (Exception e) {
		            e.printStackTrace();
		            responseModel.setStatus("Failed");
		            responseModel.setMessage("Something went Wrong !!");
		            responseModel.setCount(0);
		        }
		        return ResponseEntity.ok(responseModel);
		    }

		    @PutMapping("/")
		    public ResponseEntity<APIResponseModel> updatePet(@RequestParam("image")MultipartFile img,@ModelAttribute PetsModel petModel) {
		        responseModel = new APIResponseModel();
		        petsVec = new Vector<>();
		        try {
		        	PetsImage petImg = new PetsImage();
		        	petImg.setName(img.getOriginalFilename());
		        	petImg.setImgType(img.getContentType());
		        	petImg.setImgData(img.getBytes());
		        	petImg = this.petImgService.createPetImg(petImg);
		        	
		        	petModel.setpetsImg(petImg);
		        
		        
		            PetsModel pet = this.petService.updatePet(petModel);
		            if (pet == null) {
		                throw new Exception("error");
		            } else {
		            	petsVec.add(pet);
		                responseModel.setData(petsVec);
		                responseModel.setStatus("Successful");
		                responseModel.setMessage("Pet updated Successfully.");
		                responseModel.setCount(petsVec.size());
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            responseModel.setData(petsVec);
		            responseModel.setStatus("Error");
		            responseModel.setMessage("Something went Wrong !!");
		            responseModel.setCount(0);
		        }
		        return ResponseEntity.ok(responseModel);
		    }

		    @PostMapping("/")
		    @PreAuthorize("hasRole('ROLE_ADMIN')")
		    public ResponseEntity<APIResponseModel> createPets(@RequestParam("image")MultipartFile img,@ModelAttribute PetsModel petModel) {
		        responseModel = new APIResponseModel();
		        petsVec = new Vector<>();
		        try {
		        	PetsImage petImg = new PetsImage();
		        	petImg.setName(img.getOriginalFilename());
		        	petImg.setImgType(img.getContentType());
		        	petImg.setImgData(img.getBytes());
		        	petImg = this.petImgService.createPetImg(petImg);
		        	
		        	petModel.setpetsImg(petImg);
		            
		            PetsModel pet = this.petService.createPet(petModel);
		            petsVec.add(pet);
		            responseModel.setData(petsVec);
		            responseModel.setStatus("Successful");
		            responseModel.setMessage("All pets");
		            responseModel.setCount(petsVec.size());
		        } catch (Exception e) {
		            e.printStackTrace();
		            responseModel.setStatus("Error");
		            responseModel.setMessage("Something went Wrong !!");
		            responseModel.setCount(0);
		            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(responseModel);
		        }
		        return ResponseEntity.ok(responseModel);
		    }

}

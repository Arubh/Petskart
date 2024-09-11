package com.example.demo.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pets")
public class PetsModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;
	
	private String name;
	
	private int price;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PetsImage petsImg;

	public PetsModel(int petId, String name, int price, PetsImage petsImg) {
		super();
		this.petId = petId;
		this.name = name;
		this.price = price;
		this.petsImg = petsImg;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public PetsModel() {
		super();
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PetsImage getpetsImg() {
		return petsImg;
	}

	public void setpetsImg(PetsImage petsImg) {
		this.petsImg = petsImg;
	}
}

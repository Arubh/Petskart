package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PETS_IMAGE")
public class PetsImage {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	 private Long id;
	 private String name;
	 private String imgType;
	 @Lob
	 @Column(columnDefinition = "BLOB")
	 private byte[] imgData;
	public PetsImage(Long id, String name, String imgType, byte[] imgData) {
		super();
		this.id = id;
		this.name = name;
		this.imgType = imgType;
		this.imgData = imgData;
	}
	public PetsImage() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	public byte[] getImgData() {
		return imgData;
	}
	public void setImgData(byte[] imgData) {
		this.imgData = imgData;
	}
	 
}

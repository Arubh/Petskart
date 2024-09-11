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
@Table(name = "PRODUCTS_IMAGE")
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	 private String name;
	 private String imgType;
	 @Lob
	 @Column(columnDefinition = "BLOB")
	 private byte[] imgData;
	 public ProductImage() {
		super();
	}
	public ProductImage(Long id, String name, String imgType, byte[] imgData) {
		super();
		this.id = id;
		this.name = name;
		this.imgType = imgType;
		this.imgData = imgData;
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
	public void setImgData(byte[] img) {
		this.imgData = img;
	}
	
	 
}

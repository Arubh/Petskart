package com.example.demo.models;


import jakarta.persistence.*;


@Entity
@Table(name = "Products")
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productID;
	
	@Column(nullable = false, updatable = true)
	private String productName;
	
	@Column(nullable = false, updatable = true)
	private String productDesc;
	

	@OneToOne(cascade = CascadeType.ALL)
	private ProductImage productImg;
	

	@Column(nullable = false, updatable = true)
	private int price;

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public ProductImage getProductImg() {
		return productImg;
	}
	
	public void setProductImg(ProductImage productImg) {
		this.productImg = productImg;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ProductModel() {
		super();
	}

	public ProductModel(int productID, String productName, String productDesc, ProductImage productImg, int price) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productDesc = productDesc;
		this.productImg = productImg;
		this.price = price;
	}
	
	
	
}

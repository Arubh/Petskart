package com.example.demo.service;

import java.util.List;

import com.example.demo.models.ProductImage;

public interface ProductImageService {
	public List<ProductImage> getAllProductImg();
    
    public ProductImage createProductImg(ProductImage productImage);    

    public ProductImage getProductImgById(int productImgId);

    public void deleteProductImgById(int productImgId);
    
    public ProductImage updateProductImg(ProductImage productImage);
    
}

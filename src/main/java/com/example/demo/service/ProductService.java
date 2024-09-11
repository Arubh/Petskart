package com.example.demo.service;

import java.util.List;

import com.example.demo.models.PetsModel;
import com.example.demo.models.ProductModel;


public interface ProductService {
	
    public List<ProductModel> getAllProducts();
    
    public ProductModel createProduct(ProductModel productModel);    

    public ProductModel getProductById(int productId);

    public void deleteProductById(int productId);
    
    public ProductModel updateProduct(ProductModel productModel);
}

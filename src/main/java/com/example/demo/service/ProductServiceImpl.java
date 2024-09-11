package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.models.PetsModel;
import com.example.demo.models.ProductImage;
import com.example.demo.models.ProductModel;
import com.example.demo.repository.PetsRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.FileUtil;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository prodcutsRepository;
	
	@Override
	public List<ProductModel> getAllProducts() {
		List<ProductModel> lis = this.prodcutsRepository.findAll();
		for(ProductModel x: lis) {
			ProductImage img = x.getProductImg();
			if(img == null)continue;
			img.setImgData(FileUtil.decompressFile(img.getImgData()));
			x.setProductImg(img);
		}
		return lis;
	}

	@Override
	public ProductModel createProduct(ProductModel productModel) {
		return this.prodcutsRepository.save(productModel);
	}

	@Override
	public ProductModel getProductById(int productId) {
		ProductModel model = this.prodcutsRepository.findById(productId).get();
		ProductImage img = model.getProductImg();
		img.setImgData(FileUtil.decompressFile(img.getImgData()));
		model.setProductImg(img);
		return model; 
	}

	@Override
	public void deleteProductById(int productId) {
		ProductModel product = this.prodcutsRepository.findById(productId).get();
        this.prodcutsRepository.delete(product);
		
	}

	@Override
	public ProductModel updateProduct(ProductModel productModel) {
		if (productModel != null) {
	        return this.prodcutsRepository.save(productModel);
	    } else {
	        return null;
	    }
		
	}
	
}

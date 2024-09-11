package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.util.FileUtil;

@Service
public class ProductImageImpl implements ProductImageService{
	@Autowired
	private ProductImageRepository productRepository;
	
	@Override
	public List<ProductImage> getAllProductImg() {
		List<ProductImage> lis = this.productRepository.findAll();
		for(ProductImage x: lis) {
			x.setImgData(FileUtil.decompressFile(x.getImgData()));
		}
		return lis;
	}

	@Override
	public ProductImage createProductImg(ProductImage productImage) {
		productImage.setImgData(FileUtil.compressFile(productImage.getImgData()));
		return this.productRepository.save(productImage);
	}

	@Override
	public ProductImage getProductImgById(int productsImgId) {
		ProductImage productImage = this.productRepository.findById(productsImgId).get();
		productImage.setImgData(FileUtil.decompressFile(productImage.getImgData()));
		return productImage;
	}

	@Override
	public void deleteProductImgById(int productImgId) {
		this.productRepository.deleteById(productImgId);
	}

	@Override
	public ProductImage updateProductImg(ProductImage productImage) {
		if(productImage != null) {
			productImage.setImgData(FileUtil.compressFile(productImage.getImgData()));
			return this.productRepository.save(productImage);
			
		}else return null;
	}

}

package com.example.demo.controller;

import java.util.List;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.demo.models.ProductImage;
import com.example.demo.models.ProductModel;
import com.example.demo.service.ProductImageImpl;
import com.example.demo.service.ProductServiceImpl;
import com.example.demo.util.FileUtil;


@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private ProductImageImpl prodcutImgService;
	private APIResponseModel responseModel;

	private Vector<ProductModel> productVec;

	 
	    @GetMapping("/")
		public ResponseEntity<?> getAllProduct(){
			responseModel = new APIResponseModel();
			try {
	            List<ProductModel> allProduct = this.productService.getAllProducts();
	            productVec = new Vector<>(allProduct);
	            responseModel.setData(productVec);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Got all products");
	            responseModel.setCount(productVec.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseModel);
	        }
			return ResponseEntity.ok(responseModel);
		}
	    @GetMapping("/{productId}/img")
		public ResponseEntity<?> getProductImageById(@PathVariable("productId") int productId){
			responseModel = new APIResponseModel();
			try {
				productVec = new Vector<>();
				ProductModel product = this.productService.getProductById(productId);
	            if (product != null)
	                productVec.add(product);
	            else
	                throw new Exception("Image Not found");
	            
	            responseModel.setData(productVec);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Found Image");
	            responseModel.setCount(productVec.size());
	            byte[] imageData=product.getProductImg().getImgData();
				return ResponseEntity.status(HttpStatus.OK)
						.contentType(MediaType.valueOf(product.getProductImg().getImgType()))
						.body(imageData);
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	        }
			
			return ResponseEntity.ok(responseModel);
		}
		@GetMapping("/{productId}")
		public ResponseEntity<?> getProductById(@PathVariable("productId") int productId){
			responseModel = new APIResponseModel();
			try {
				productVec = new Vector<>();
				ProductModel product = this.productService.getProductById(productId);
	            if (product != null)
	                productVec.add(product);
	            else
	                throw new Exception("User Not found");
	            
	            responseModel.setData(productVec);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Found Product");
	            responseModel.setCount(productVec.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	        }
			return ResponseEntity.ok(responseModel);
		}
		
		@DeleteMapping("/{productId}")
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		public ResponseEntity<?> deleteProductById(@PathVariable int productId) {
		        responseModel = new APIResponseModel();
		        productVec = new Vector<>();
		        try {
		            this.productService.deleteProductById(productId);
		            responseModel.setData(productVec);
		            responseModel.setStatus("Successful");
		            responseModel.setMessage("Deleted successfully");
		            responseModel.setCount(productVec.size());
		        } catch (Exception e) {
		            e.printStackTrace();
		            responseModel.setStatus("Failed");
		            responseModel.setMessage("Something went Wrong !!");
		            responseModel.setCount(0);
		        }
		        return ResponseEntity.ok(responseModel);
		    }

		    @PutMapping("/")
		    @PreAuthorize("hasRole('ROLE_ADMIN')")
		    public ResponseEntity<APIResponseModel> updateProduct(@RequestParam("image")MultipartFile img,@ModelAttribute ProductModel productModel) {
		        responseModel = new APIResponseModel();
		        productVec = new Vector<>();
		        try {
		        	ProductImage productImg = new ProductImage();
		        	productImg.setName(img.getOriginalFilename());
		        	productImg.setImgType(img.getContentType());
		        	productImg.setImgData(img.getBytes());
		        	productImg = this.prodcutImgService.createProductImg(productImg);
		        	
		        	productModel.setProductImg(productImg);
		        	
		            ProductModel product = this.productService.updateProduct(productModel);
		            if (product == null) {
		                throw new Exception("error");
		            } else {
		            	productVec.add(product);
		                responseModel.setData(productVec);
		                responseModel.setStatus("Successful");
		                responseModel.setMessage("Product updated Successfully.");
		                responseModel.setCount(productVec.size());
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            responseModel.setData(productVec);
		            responseModel.setStatus("Error");
		            responseModel.setMessage("Something went Wrong !!");
		            responseModel.setCount(0);
		        }
		        return ResponseEntity.ok(responseModel);
		    }
			 @PostMapping("/")
			 @PreAuthorize("hasRole('ROLE_ADMIN')")
			    public ResponseEntity<APIResponseModel> createProduct(@RequestParam("image")MultipartFile img,@ModelAttribute ProductModel productModel) {
			        responseModel = new APIResponseModel();
			        productVec = new Vector<>();
			        try {
			        	ProductImage productImg = new ProductImage();
			        	productImg.setName(img.getOriginalFilename());
			        	productImg.setImgType(img.getContentType());
			        	productImg.setImgData(img.getBytes());
			        	productImg = this.prodcutImgService.createProductImg(productImg);
			        	
			        	productModel.setProductImg(productImg);
			            ProductModel product = this.productService.createProduct(productModel);
			            productVec.add(product);
			            responseModel.setData(productVec);
			            responseModel.setStatus("Successful");
			            responseModel.setMessage("All product");
			            responseModel.setCount(productVec.size());
			        } catch (Exception e) {
			            e.printStackTrace();
			            responseModel.setStatus("Error");
			            responseModel.setMessage("Something went Wrong !!");
			            responseModel.setCount(0);
			        }
			        return ResponseEntity.ok(responseModel);
			    }
		   
}

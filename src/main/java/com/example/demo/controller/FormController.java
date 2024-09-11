package com.example.demo.controller;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.apiresponsemodel.APIResponseModel;
import com.example.demo.models.FormData;
import com.example.demo.models.PetsModel;
import com.example.demo.repository.FormDataRepository;

@RestController
@RequestMapping("/formdata")
@CrossOrigin
public class FormController {
//    private final JavaMailSender emailSender;
    private final FormDataRepository formDataRepository;
    private APIResponseModel responseModel;
    Vector<FormData> formDataList;
    @Autowired
    public FormController(FormDataRepository formDataRepository) {
      
        this.formDataRepository = formDataRepository;
    }

    @PostMapping("/")
	public ResponseEntity<?> saveFormDataAndSendEmail(@ModelAttribute FormData formData) {
		responseModel = new APIResponseModel();
		try {
			this.formDataRepository.save(formData);
            formDataList = new Vector<>();
            formDataList.add(formData);
            
            responseModel.setData(formDataList);
            responseModel.setStatus("Successful");
            responseModel.setMessage("Created new formdata record");
            responseModel.setCount(formDataList.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setStatus("Failed");
            responseModel.setMessage("Something went Wrong !!");
            responseModel.setCount(0);
        }
		return ResponseEntity.ok(responseModel);
	}

 
    @GetMapping("/")
	public ResponseEntity<?> getdata(){
		responseModel = new APIResponseModel();
		try {
            formDataList = new Vector<>(this.formDataRepository.findAll());
            
            responseModel.setData(formDataList);
            responseModel.setStatus("Successful");
            responseModel.setMessage("Got all formdata");
            responseModel.setCount(formDataList.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setStatus("Failed");
            responseModel.setMessage("Something went Wrong !!");
            responseModel.setCount(0);
        }
		return ResponseEntity.ok(responseModel);
	}
    @GetMapping("/{formId}")
	public ResponseEntity<?> getFromById(@PathVariable int formId) {
	        responseModel = new APIResponseModel();
	        formDataList = new Vector<>();
	        try {
	        	formDataList.add(this.formDataRepository.findById((long) formId).get());
               
	            responseModel.setData(formDataList);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Found form data successfully");
	            responseModel.setCount(formDataList.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	        }
	        return ResponseEntity.ok(responseModel);
	    }
    @DeleteMapping("/{formId}")
	public ResponseEntity<?> deleteFromById(@PathVariable int formId) {
	        responseModel = new APIResponseModel();
	        formDataList = new Vector<>();
	        try {
	        	formDataList.add(this.formDataRepository.findById((long) formId).get());
	            this.formDataRepository.deleteById((long) formId);
	            
	            
	            responseModel.setData(formDataList);
	            responseModel.setStatus("Successful");
	            responseModel.setMessage("Deleted successfully");
	            responseModel.setCount(formDataList.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseModel.setStatus("Failed");
	            responseModel.setMessage("Something went Wrong !!");
	            responseModel.setCount(0);
	        }
	        return ResponseEntity.ok(responseModel);
	    }

}
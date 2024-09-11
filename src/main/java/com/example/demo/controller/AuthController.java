package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import org.hibernate.usertype.UserVersionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.example.demo.auth.model.APILoginReturnModel;
import com.example.demo.auth.model.JwtRequestModel;
import com.example.demo.auth.model.SignupRequest;
import com.example.demo.auth.model.UserLoginDetailsModel;
import com.example.demo.auth.model.UserModel;
import com.example.demo.jwt.JwtUtils;
import com.example.demo.models.PetsImage;
import com.example.demo.models.PetsModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/logout")
	public ResponseEntity<String> logoutUser() {
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok("Logout Successful");
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtRequestModel jwtRequest) throws Exception {
		System.out.println("Email " + jwtRequest.getEmail());
		System.out.println("Password " + jwtRequest.getPassword());
		
		String message = "";
		String token = "";
		APILoginReturnModel apiLoginReturnModel = new APILoginReturnModel();
		apiLoginReturnModel.setStatus("fail");
		try {
			message = authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		} catch (UsernameNotFoundException e) {

			e.printStackTrace();
			message = "User not found" + e.getMessage();

		} catch (Exception e) {
			e.printStackTrace();
			message = "Some error occurred " + e.getMessage();
		}
		System.out.println("message " + message);
		UserLoginDetailsModel userLoginDetail = new UserLoginDetailsModel();
		if (message == "success") {
			final UserDetails userDetails = this.userService.loadUserByUsername(jwtRequest.getEmail());
			token = this.jwtUtils.generateToken(userDetails);
			System.out.println("Generated token " + token);
			apiLoginReturnModel.setStatus("Success");
			message = "Login Successfully";
			UserModel user = this.userService.getByEmail(userDetails.getUsername());
			System.out.println("userId " + user.getUserId());
			System.out.println("userName " + user.getUserName());
			System.out.println("emailId " + user.getEmail());
			userLoginDetail.setUserId(user.getUserId());
			userLoginDetail.setUserName(user.getUserName());

			userLoginDetail.setEmail(user.getEmail());
			userLoginDetail.setRoles(user.getRoles());

		}
		apiLoginReturnModel.setMessage(message);
		apiLoginReturnModel.setToken(token);
		apiLoginReturnModel.setUser(userLoginDetail);
		if (apiLoginReturnModel.getStatus() == "fail")
			return ResponseEntity.status(401).body(apiLoginReturnModel);
//		System.out.println(apiLoginReturnModel);
		return ResponseEntity.status(200).body(apiLoginReturnModel);
	}

	private String authenticate(String email, String password) throws Exception {
		String message = "";
		try {
			message = "success";
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, (password)));
		} catch (DisabledException e) {
			message = "User is disabled";
			// throw new Exception("User Disabled "+e.getMessage());
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			message = "Invalid Credentials";
			throw new Exception("Invalid Credentials " + e.getMessage());
		}
		return message;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		System.out.println("Entered Signup");
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.status(409).body("Error: Email is already in use!");
		}

		// Create new user's account
		UserModel user = new UserModel();

		Set<String> strRoles = signUpRequest.getRoles();
		System.out.println(signUpRequest.toString());
		System.out.println(strRoles);
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setEmail(signUpRequest.getEmail());
		user.setRoles(signUpRequest.getRoles());
		user.setUserName(signUpRequest.getUsername());

		userRepository.save(user);

		return ResponseEntity.ok("User registered successfully!");
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable int userId) {
		APIResponseModel responseModel = new APIResponseModel();
		Vector<UserModel> usersVec = new Vector<>();
		try {
			userRepository.deleteById(userId);
			responseModel.setData(usersVec);
			responseModel.setStatus("Successful");
			responseModel.setMessage("Deleted successfully");
			responseModel.setCount(usersVec.size());
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatus("Failed");
			responseModel.setMessage("Something went Wrong !!");
			responseModel.setCount(0);
		}
		return ResponseEntity.ok(responseModel);
	}

	@GetMapping("/user/")
	public ResponseEntity<?> getAllUsers() {
		APIResponseModel responseModel = new APIResponseModel();
		Vector<UserModel> usersVec;
		
		try {
			usersVec = new Vector<>(this.userRepository.findAll());	
			responseModel.setData(usersVec);
			responseModel.setStatus("Successful");
			responseModel.setMessage("Got all users");
			responseModel.setCount(usersVec.size());
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatus("Failed");
			responseModel.setMessage("Something went Wrong !!");
			responseModel.setCount(0);
		}
		return ResponseEntity.ok(responseModel);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
		APIResponseModel responseModel = new APIResponseModel();
		Vector<UserModel> userVec;
		try {
			userVec = new Vector<>();
			UserModel user = this.userRepository.findById(userId).get();
			if (user != null)
				userVec.add(user);
			else
				throw new Exception("User Not found");

			responseModel.setData(userVec);
			responseModel.setStatus("Successful");
			responseModel.setMessage("Found user");
			responseModel.setCount(userVec.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatus("Failed");
			responseModel.setMessage("Something went Wrong !!");
			responseModel.setCount(0);
		}
		return ResponseEntity.ok(responseModel);
	}


	
	@PutMapping("/user/update/")
	public ResponseEntity<?> updatePet(@RequestBody UserModel userMod) {
		APIResponseModel responseModel = new APIResponseModel();
		Vector<UserModel> usersVec = new Vector<>();
		try {
			UserModel user = this.userRepository.getById(userMod.getUserId());
			if (user == null) {
				throw new Exception("error");
			} else {
				if(userMod.getPassword() != null)user.setPassword(encoder.encode(userMod.getPassword()));
				if(userMod.getEmail() != null)user.setEmail(userMod.getEmail());
				if(userMod.getRoles() != null)user.setRoles(userMod.getRoles());
				if(userMod.getUserName() != null)user.setUserName(userMod.getUserName());
				
				usersVec.add(userRepository.save(user));
				responseModel.setData(usersVec);
				responseModel.setStatus("Successful");
				responseModel.setMessage("User updated Successfully.");
				responseModel.setCount(usersVec.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setData(usersVec);
			responseModel.setStatus("Error");
			responseModel.setMessage("Something went Wrong !!");
			responseModel.setCount(0);
		}
		return ResponseEntity.ok(responseModel);
	}

}

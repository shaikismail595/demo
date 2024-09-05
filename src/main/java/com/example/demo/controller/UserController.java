package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);


	@GetMapping("/userInfo")
	public ResponseEntity<User> getUserInfo() {
		logger.info("getUserInfo method started..");
		User user = new User("John Doe", "1234567890", "en-US", "johndoe@example.com", "johndoe", "John", "Doe",
				"America/Los_Angeles", true, "Doe", "John", "admin,user", "standard", "user123", "groupA,groupB");
		logger.info("getUserInfo method ended..");
		return ResponseEntity.ok(user);

	}


	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	
}

package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtUtils jwtUtils;

	@GetMapping("/userInfo")
	public ResponseEntity<User> getUserInfo() {
		logger.info("getUserInfo method started..");
		User user = new User("John Doe", "1234567890", "en-US", "johndoe@example.com", "johndoe", "John", "Doe",
				"America/Los_Angeles", true, "Doe", "John", "admin,user", "standard", "user123", "groupA,groupB");
		logger.info("getUserInfo method ended..");
		return ResponseEntity.ok(user);

	}

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/addNewUser")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}

	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('Admin')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@GetMapping("/users")
	public UserInfo getAllUsers() {
		return new UserInfo(1, "abba", "abba@wsz.com", "1234", "ROLE_USER");
	}
}

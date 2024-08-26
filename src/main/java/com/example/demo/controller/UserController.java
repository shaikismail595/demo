package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtUtils jwtUtils;

//	@Autowired
//	private AuthenticationManager authenticationManager;

	@GetMapping("/userInfo")
	public ResponseEntity<User> getUserInfo() {
		User user = new User("John Doe", "1234567890", "en-US", "johndoe@example.com", "johndoe", "John", "Doe",
				"America/Los_Angeles", true, "Doe", "John", "admin,user", "standard", "user123", "groupA,groupB");

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
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//		if (true) {
		return jwtUtils.generateToken(authRequest.getUsername());
//		} else {
//			throw new UsernameNotFoundException("Invalid user request!");
//		}
	}

	@GetMapping("/users")
	public UserInfo getAllUsers() {
		return new UserInfo(1, "abba", "abba@wsz.com", "1234", "ROLE_USER");
	}
}

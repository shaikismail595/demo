package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserController userController;

	@Test
	public void testWelcomeEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/auth/welcome")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Welcome this endpoint is not secure"));
	}

	@Test
	public void testAddNewUserEndpoint() throws Exception {
		UserInfo userInfo = new UserInfo(1, "john", "john@example.com", "password", "ROLE_USER");
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/addNewUser").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userInfo))).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testUserProfileEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/auth/user/userProfile"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Welcome to User Profile"));
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void testAdminProfileEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/auth/admin/adminProfile"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Welcome to Admin Profile"));
	}

	@Test
	public void testGenerateTokenEndpoint() throws Exception {
		AuthRequest authRequest = new AuthRequest("user", "password");
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/generateToken").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(authRequest)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetAllUsersEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/auth/users")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetUserInfo_ReturnsExpectedUser() throws Exception {
		User expectedUser = new User("John Doe", "1234567890", "en-US", "johndoe@example.com", "johndoe", "John", "Doe",
				"America/Los_Angeles", true, "Doe", "John", "admin,user", "standard", "user123", "groupA,groupB");
		ResponseEntity<User> response = userController.getUserInfo();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedUser, response.getBody());
	}
}
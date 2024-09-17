package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserController userController;

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void testAdminProfileEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/auth/admin/adminProfile"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
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
package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;

@ExtendWith({ MockitoExtension.class })
public class UserInfoServiceTest {
	@Mock
	private UserInfoRepository repository;


	@InjectMocks
	private UserInfoService userInfoService;

	@Test
	public void testLoadUserByUsername_ExistingUser() {
		UserInfo userInfo = new UserInfo(1, "john", "john@example.com", "password", "ROLE_USER");
		when(repository.findByEmail("john@example.com")).thenReturn(userInfo);
		UserDetails userDetails = userInfoService.loadUserByUsername("john@example.com");
		assertEquals(userInfo.getName(), userDetails.getUsername());
		assertEquals(userInfo.getPassword(), userDetails.getPassword());
	}

	@Test
    public void testLoadUserByUsername_NonExistingUser() {
        when(repository.findByEmail("nonexisting@example.com")).thenReturn(null);
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername("nonexisting@example.com"));
        assertEquals("User not found: nonexisting@example.com", exception.getMessage());
    }

}

package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.UserInfo;

@ExtendWith(MockitoExtension.class)
public class UserInfoRepositoryTest {
    @InjectMocks
    private UserInfoRepository userInfoRepository ;

    @Test
    public void testFindByEmail() {
        UserInfo userInfo = userInfoRepository.findByEmail("abba@wsz.com");

        assertEquals(1, userInfo.getId());
        assertEquals("abba", userInfo.getName());
        assertEquals("abba@wsz.com", userInfo.getEmail());
        assertEquals("1234", userInfo.getPassword());
        assertEquals("ROLE_USER", userInfo.getRoles());
    }

  
}

package com.example.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserInfo;

@Repository
public class UserInfoRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoRepository.class);

	public UserInfo findByEmail(String email) {
		return new UserInfo(1, "abba", "abba@wsz.com","1234","ROLE_USER");
	} 
}

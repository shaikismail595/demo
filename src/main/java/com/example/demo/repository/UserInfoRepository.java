package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.UserInfo;

@Repository
public class UserInfoRepository {
	public UserInfo findByEmail(String email) {
		return new UserInfo(1, "abba", "abba@wsz.com","1234","ROLE_USER");
	} 
	public String save(UserInfo userInfo) {
		return "User Added Successfully";
	}
}

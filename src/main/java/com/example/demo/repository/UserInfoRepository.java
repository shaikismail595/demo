package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.UserInfo;

@Repository
public class UserInfoRepository {
	public UserInfo findByEmail(String email) {
		return new UserInfo(1, "abba", "abba@wsz.com","1234","ROLE_USER");
	} // Use 'email' if that is the correct field for login

	public void save(UserInfo userInfo) {
	}
}

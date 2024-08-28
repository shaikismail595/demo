package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.ApplicationDetailsController;
import com.example.demo.model.UserInfo;
import com.example.demo.model.UserInfoDetails;
import com.example.demo.repository.UserInfoRepository;

import java.util.Optional;

@Service
@EnableCaching
public class UserInfoService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
    

	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	@Cacheable("userInfo")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		doLongRunningTask();
		Optional<UserInfo> userDetail = Optional.ofNullable(repository.findByEmail(username));
		// Converting UserInfo to UserDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		return repository.save(userInfo);
	}

	private void doLongRunningTask() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

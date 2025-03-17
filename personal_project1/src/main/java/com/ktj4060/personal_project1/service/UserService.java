package com.ktj4060.personal_project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktj4060.personal_project1.entity.User;
import com.ktj4060.personal_project1.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public void insertUser(User user) {
		userMapper.insertUser(user);
	}
	
	public String findWriter(String username) {
		return userMapper.findWriter(username);
	}
	
}

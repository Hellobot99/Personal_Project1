package com.ktj4060.personal_project1.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ktj4060.personal_project1.entity.CustomUser;
import com.ktj4060.personal_project1.entity.User;
import com.ktj4060.personal_project1.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userMapper.findByUsername(username);
		if(user == null) {
			//데이터가 없으면
			throw new UsernameNotFoundException(username+" 존재하지 않습니다.");
		}
		
		return new CustomUser(user);
	}
	
}

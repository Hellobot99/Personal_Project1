package com.ktj4060.personal_project1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ktj4060.personal_project1.entity.User;

@Mapper //자바와 mysql을 연결
public interface UserMapper {

	@Insert("INSERT INTO backend_spring_project.user(username,password,writer,role) "
			+ "VALUES(#{username},#{password},#{writer},#{role})")
	void insertUser(User user);
	
	@Select("SELECT username,password,writer,role FROM backend_spring_project.user WHERE username=#{username}")
	User findByUsername(String username);
	
	@Select("SELECT writer FROM backend_spring_project.user WHERE username=#{username}")
	String findWriter(String username);
}

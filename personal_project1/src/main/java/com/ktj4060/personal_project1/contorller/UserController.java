package com.ktj4060.personal_project1.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktj4060.personal_project1.entity.Role;
import com.ktj4060.personal_project1.entity.User;
import com.ktj4060.personal_project1.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user) {
		String userPassword = user.getPassword();
		System.out.println("userPassword:"+userPassword);
		user.setRole(Role.MEMBER);
		String passwordEncoded = passwordEncoder.encode(userPassword);
		user.setPassword(passwordEncoded);
		userService.insertUser(user);
		System.out.println("userPassword:"+passwordEncoded);
		return "redirect:/loginPage";
	}
}

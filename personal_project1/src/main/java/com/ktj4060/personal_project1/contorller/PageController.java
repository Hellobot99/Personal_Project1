package com.ktj4060.personal_project1.contorller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktj4060.personal_project1.entity.User;


import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PageController {
	
	// only / == localhost:8080
	
	@GetMapping("/") 
	public String home() {
		return "index";		
	}
	
	@GetMapping("/registerPage")
	public String registerPage(HttpServletRequest request, Model model) {
		CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
		model.addAttribute("_csrf",csrfToken);
		return "register/index";		
	}
	
	@GetMapping("/loginPage")
	public String loginPage(HttpServletRequest request, Model model) {
		CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
		model.addAttribute("_csrf",csrfToken);
		return "login/index";		
	}	
}

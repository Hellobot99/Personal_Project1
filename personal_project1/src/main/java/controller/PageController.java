package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	// only / == localhost:8080
	
	@GetMapping("/") 
	public String home() {
		return "index";		
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "register/index";		
	}
	
	@GetMapping("/loginPage")
	public String loginPage() {
		return "asd";
	}
	
	
}

package com.springboot.jpa.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springboot.jpa.hibernate.service.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	//@Autowired
	//private MyUserDetailsService userDetailsManager;
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/admin/home")
	public String adminHome() {
		return "adminHome";
	}

	@GetMapping("user/home")
	public String userHome() {
		return "userHome";
	}

	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("username") String username) {
		session.setAttribute("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		model.addAttribute("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		System.out.println("............xx@GetMapping(\"/login\") " + username);
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
//
//	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
//			MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
//	public void addUser(@RequestParam Map<String, String> body) {
//		User user = new User();
//		user.setUsername(body.get("username"));
//		user.setPassword(passwordEncoder.encode(body.get("password")));
//		user.setAccountNonLocked(true);
//		userDetailsManager.createUser(user);
//	}

	private String getErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception) request.getSession().getAttribute(key);
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!!";
		} else if (exception instanceof LockedException) {
			error = "An authentication request is rejected because the account is locked";
		} else {
			if (exception != null)
				error = exception.getMessage();
		}
		return error;
	}
}

//package com.springboot.jpa.hibernate.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.springboot.jpa.hibernate.service.SecurityService;
//
//@RestController
//public class LoginController {
//	  @Autowired
//	    private SecurityService securityService;
//	  @Autowired
//	private   AuthenticationManager authenticationManager;
//
//	public LoginController(AuthenticationManager authenticationManager) {
//		this.authenticationManager = authenticationManager;
//	}
//
//	@PostMapping("/login")
//	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
//		Authentication authenticationRequest =
//			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
//		Authentication authenticationResponse =
//			this.authenticationManager.authenticate(authenticationRequest);
//		System.out.println(".............. /login");
//		System.out.println(authenticationRequest.getCredentials());
//		System.out.println(authenticationResponse.getCredentials());
//		
//		return (ResponseEntity<Void>) authenticationResponse;
//	}

//	 @GetMapping("/login")
//	    public String login(Model model, String error, String logout) {
//	        if (securityService.isAuthenticated()) {
//	            return "redirect:/";
//	        }
//
//	        if (error != null)
//	            model.addAttribute("error", "Your username and password is invalid.");
//
//	        if (logout != null)
//	            model.addAttribute("message", "You have been logged out successfully.");
//
//	        return "login";
//	    }
//	 
//	public record LoginRequest(String username, String password) {
//	}
//
//}
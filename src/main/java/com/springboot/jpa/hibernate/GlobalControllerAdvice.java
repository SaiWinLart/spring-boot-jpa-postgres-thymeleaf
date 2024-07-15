package com.springboot.jpa.hibernate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springboot.jpa.hibernate.service.UserPrincipal;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute
	public void addAttributes(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
			model.addAttribute("loginUsername", user.getUser().getUsername()); 
		}
	}
}

package com.springboot.jpa.hibernate;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	public AuthenticationSuccessHandler() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
	 System.out.println(" grantedAuthority.getAuthority()............" + authentication.getAuthorities().toString());
		String targetUrl;
		if (isAdmin) {

			targetUrl = "/admin/home";

			response.sendRedirect(targetUrl);
		} else {
			targetUrl = "/user/home";
			response.sendRedirect(targetUrl);
		}

	}
}

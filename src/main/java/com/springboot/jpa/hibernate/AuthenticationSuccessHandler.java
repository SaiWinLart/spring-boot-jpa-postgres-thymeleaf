package com.springboot.jpa.hibernate;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.springboot.jpa.hibernate.service.UserPrincipal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	public AuthenticationSuccessHandler() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		System.out.println(" grantedAuthority.getAuthority()............" + authentication.getAuthorities().toString());
		String targetUrl;
		
		if (authentication.getPrincipal() instanceof UserPrincipal) {
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
			session.setAttribute("passwordChangeRequired", user.getUser().isNeedsPasswordChange());
			if (user.getUser().isNeedsPasswordChange()) {
				targetUrl = "/change-password";
				response.sendRedirect(targetUrl);
			} else if (isAdmin) {
				targetUrl = "/admin/home";
				response.sendRedirect(targetUrl);
			} else {
				targetUrl = "/user/home";
				response.sendRedirect(targetUrl);
			}
		}

	}
}

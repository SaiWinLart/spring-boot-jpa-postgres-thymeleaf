package com.springboot.jpa.hibernate;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.jpa.hibernate.model.User;
import com.springboot.jpa.hibernate.respository.IUserRepository;
import com.springboot.jpa.hibernate.service.UserPrincipal;

import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationEventListener {
	private final IUserRepository userRepository;

	public AuthenticationEventListener(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@EventListener
	public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
		 String username = (String) event.getAuthentication().getPrincipal();
		//UserPrincipal loginUser = (UserPrincipal) event.getAuthentication().getPrincipal();
		updateFailedAttemptCount(username);
		
	}

	@EventListener
	public void authenticationSucceeded(AuthenticationSuccessEvent event) {
		//UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal(); 
		UserPrincipal loginUser = (UserPrincipal) event.getAuthentication().getPrincipal(); 
		resetFailedAttemptCount(loginUser.getUser().getUsername());
	}

	public void updateFailedAttemptCount(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setFailedAttemptCount(user.getFailedAttemptCount() + 1);
			userRepository.save(user);
		}
	}
	
	public void resetFailedAttemptCount(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setFailedAttemptCount(0);
			userRepository.save(user);
		}
	}
	

}

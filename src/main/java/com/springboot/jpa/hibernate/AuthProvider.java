//package com.springboot.jpa.hibernate;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import com.springboot.jpa.hibernate.model.Attempts;
//import com.springboot.jpa.hibernate.model.User;
//import com.springboot.jpa.hibernate.respository.IAttemptsRepository;
//import com.springboot.jpa.hibernate.respository.IUserRepository;
//import com.springboot.jpa.hibernate.service.SecurityUserDetailsService;
//
//@Component
//public class AuthProvider implements AuthenticationProvider {
//	private static final int ATTEMPTS_LIMIT = 3;
//	  @Autowired private SecurityUserDetailsService userDetailsService;
//	// @Autowired private PasswordEncoder passwordEncoder;
//	@Autowired
//	private IAttemptsRepository attemptsRepository;
//	@Autowired
//	private IUserRepository userRepository;
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		String username = authentication.getName();
//		System.out.println(username);
//		Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(username);
//	
//		if (userAttempts.isPresent()) {
//			System.out.println(userAttempts.get().getUsername());
//			Attempts attempts = userAttempts.get();
//			attempts.setAttempts(0);
//			attemptsRepository.save(attempts);
//		}
//		System.out.println(authentication.isAuthenticated());
//		System.out.println(authentication.getPrincipal());
//		System.out.println(authentication.getName());
//		System.out.println(authentication);
//		processFailedAttempts(username, new User());
//		 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//		return new UsernamePasswordAuthenticationToken(userDetailsService, null, userDetails.getAuthorities());
//	}
//
//	private void processFailedAttempts(String username, User user) {
//		Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(username);
//		if (userAttempts.isEmpty()) {
//			Attempts attempts = new Attempts();
//			attempts.setUsername(username);
//			attempts.setAttempts(1);
//			attemptsRepository.save(attempts);
//		} else {
//			Attempts attempts = userAttempts.get();
//			attempts.setAttempts(attempts.getAttempts() + 1);
//			attemptsRepository.save(attempts);
//
//			if (attempts.getAttempts() + 1 > ATTEMPTS_LIMIT) {
//				user.setAccountNonLocked(false);
//				userRepository.save(user);
//				throw new LockedException("Too many invalid attempts. Account is locked!!");
//			}
//		}
//	}
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return true;
//	}
//}
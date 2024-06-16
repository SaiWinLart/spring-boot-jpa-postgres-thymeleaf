package com.springboot.jpa.hibernate.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.jpa.hibernate.model.User;
import com.springboot.jpa.hibernate.respository.IUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final IUserRepository userRepository;

	public MyUserDetailsService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + username);
		}

		return new UserPrincipal(user);
	}

	public void createUser(User user) {
		userRepository.save(user);
		
	}
}

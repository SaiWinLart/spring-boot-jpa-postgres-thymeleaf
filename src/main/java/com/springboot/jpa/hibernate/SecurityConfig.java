package com.springboot.jpa.hibernate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeHttpRequests(registry -> {
			registry.requestMatchers("/init-admin").permitAll();
			registry.requestMatchers("/change-password").permitAll();
			registry.requestMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER").requestMatchers("/user/**")
					.hasAnyRole("USER", "ADMIN", "MANAGER").requestMatchers("/**")
					.hasAnyRole("USER", "ADMIN", "MANAGER").anyRequest().hasAnyRole("MANAGER", "ADMIN");

		}).formLogin(httpSecurityFormLoginConfigurer -> {
			httpSecurityFormLoginConfigurer.loginPage("/login").successHandler(new AuthenticationSuccessHandler())
					.permitAll();
		}).build();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		// authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		// System.out.println(".................authProvider..AuthenticationManager...");
		return authenticationProvider;
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

}
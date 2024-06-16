package com.springboot.jpa.hibernate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//	@Bean
//	public UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		String encodedPassword = passwordEncoder().encode("user1234");
//		System.out.println("....UserDetailsService......" + encodedPassword);
//		manager.createUser(User.withUsername("user").password(encodedPassword).roles("USER").build());
//		return manager;
//	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests(
//				(authorize) -> authorize.requestMatchers("/login").permitAll().anyRequest().authenticated())
//				.httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());
//		System.out.println("....securityFilterChain......"+http );
//		return http.build();
//	}
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests((authz) -> authz
//            .requestMatchers("/admin/**").hasRole("ADMIN")
//            .requestMatchers("/user/**").hasRole("USER")
//            .anyRequest().authenticated()
//        );
//    return http.build();
//} 

//for MVC
	// https://www.youtube.com/watch?v=9J-b6OlPy24&ab_channel=GenuineCoder
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(registry -> {
			registry.requestMatchers("/").permitAll();
			registry.anyRequest().authenticated();
		}).formLogin(httpSecurityFormLoginConfigurer -> {
			httpSecurityFormLoginConfigurer.loginPage("/login").permitAll();

		}).build();
	}
//	private   PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//	@Bean
//	public AuthenticationManager authenticationManager() { 
//	  // Configure a DaoAuthenticationProvider
//	  DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//	  authenticationProvider.setUserDetailsService(userDetailsService);
//	  authenticationProvider.setPasswordEncoder(passwordEncoder);
//	  ProviderManager providerManager = new ProviderManager(Collections.singletonList(authenticationProvider));
//
//	  return providerManager;
//	    
//	}


	private final UserDetailsService userDetailsService;

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		// System.out.println("...AuthenticationManager..."+passwordEncoder);
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		// authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		System.out.println(".authProvider..AuthenticationManager...");
		return authenticationProvider;
	}
	 

}
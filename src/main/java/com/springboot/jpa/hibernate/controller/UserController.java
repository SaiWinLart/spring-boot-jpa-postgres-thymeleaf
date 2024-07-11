package com.springboot.jpa.hibernate.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.jpa.hibernate.model.Role;
import com.springboot.jpa.hibernate.model.Roles;
import com.springboot.jpa.hibernate.model.User;
import com.springboot.jpa.hibernate.service.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	@Autowired
	private MyUserDetailsService userDetailsManager;

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

	@GetMapping("/init-admin")
	public String initAdmin(Model model) {
		UserDetails admin = null;
		try {
			admin = userDetailsManager.loadUserByUsername("admin");
		} catch (Exception e) {

		}
		if (admin == null) {
			createInitialAdmin();
			model.addAttribute("infoMessage", "Admin init successfully!");
		} else {
			model.addAttribute("infoMessage", "Admin already exist!");
		}

		return "login";
	}

	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("username") String username) {
		session.setAttribute("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		model.addAttribute("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

		return "login";
	}

	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/admin/register-user")
	public String showRegistrationForm(Model model) {
		// model.addAttribute("allRoles", userDetailsManager.getAllrole());
		List<Long> roles = new ArrayList<>();
		model.addAttribute("selectedRoles", roles);
		model.addAttribute("user", new User());
		return "userRegistration";
	}

	@ModelAttribute("allRoles")
	public Roles[] populateRoles() {
		return Roles.values();
	}

	@PostMapping("admin/register-user")
	public String registerUser(@ModelAttribute("user") @Valid User user, @RequestParam List<String> selectedRoles,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			ObjectError error = bindingResult.getGlobalError();
			bindingResult.addError(error);
			model.addAttribute("errorMessage", error);
			return "userRegistration";
		}
		try {
			createDefaultUser(user, selectedRoles);
			model.addAttribute("successMessage", "User : " + user.getUsername() + " registered successfully!");

		} catch (Exception e) {

			model.addAttribute("errorMessage",
					user.getUsername() + "  already exists. Please input another username.!");
		}

		return "userRegistration";
	}

	private void createDefaultUser(User userObj, List<String> rolesId) {
		User user = new User();
		user.setUsername(userObj.getUsername());
		user.setAccountNonLocked(true);
		user.setFailedAttemptCount(0);
		passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode("123"));

		List<Role> selectedRoles = convertRoleIdsToRoles(rolesId, userDetailsManager);
		user.setRoles(selectedRoles);

		userDetailsManager.createUser(user);
	}

	private void createInitialAdmin() {
		User user = new User();
		user.setUsername("admin");
		user.setAccountNonLocked(true);
		user.setFailedAttemptCount(0);
		passwordEncoder = new BCryptPasswordEncoder();

		user.setPassword(passwordEncoder.encode("admin"));
		List<Role> adminRole = new ArrayList<Role>();

		adminRole.add(userDetailsManager.createRole(new Role("ROLE_ADMIN")));

		user.setRoles(adminRole);
		userDetailsManager.createUser(user);
	}

	public List<Role> convertRoleIdsToRoles(List<String> roleNameList, MyUserDetailsService userDetailsManager) {
		if (roleNameList == null || roleNameList.isEmpty()) {
			return Collections.emptyList();
		}

		List<Role> roles = new ArrayList<>();
		for (String roleName : roleNameList) {
			Role role = userDetailsManager.getRoleByName(roleName);
			if (role == null) {
				role = createNewRole(roleName);
			}

			roles.add(role);
		}
		return roles;
	}

	private Role createNewRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return userDetailsManager.createRole(role);
	}

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

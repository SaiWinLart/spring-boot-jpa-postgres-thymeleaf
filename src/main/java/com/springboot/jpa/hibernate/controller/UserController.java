package com.springboot.jpa.hibernate.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.jpa.hibernate.model.Role;
import com.springboot.jpa.hibernate.model.User;
import com.springboot.jpa.hibernate.service.MyUserDetailsService;
import com.springboot.jpa.hibernate.service.UserPrincipal;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	private String defaultPassword = "123";
	// @Autowired
	private MyUserDetailsService userDetailsManager;
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private String defaultAdminPassword = "admin";

	public UserController(MyUserDetailsService userDetailsManager) {
		super();
		this.userDetailsManager = userDetailsManager;
	}

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

	@GetMapping("/change-password")
	public String changeDefaultPassword(Model model) {

		return "changePassword";
	}

	@PostMapping("/admin/reset-user-password")
	public String resetToDefaultPassword(@RequestParam("userId") Long userId, Model model,
			RedirectAttributes redirectAttributes) {

		User user = userDetailsManager.getUserById(userId);
		user.setPassword(passwordEncoder.encode(defaultPassword));
		user.setNeedsPasswordChange(true);
		userDetailsManager.createUser(user);
		redirectAttributes.addFlashAttribute("infoMessage", "Successfully reset the password.");

		return "redirect:/admin/get-all-users";
	}

	@PostMapping("/change-password")
	public String changePassword(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmNewPassword") String confirmNewPassword,
			@AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes redirectAttributes) {

		UserPrincipal loginUser = (UserPrincipal) userDetails;
		User user = userDetailsManager.getUserByusername(loginUser.getUser().getUsername());

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {

			model.addAttribute("errorMessage", "Old password is incorrect!");
			return "changePassword";
		}

		if (!newPassword.equals(confirmNewPassword)) {
			model.addAttribute("errorMessage", "New password and confirm password do not match!");
			return "changePassword";
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		user.setNeedsPasswordChange(false);
		userDetailsManager.createUser(user);
		session.setAttribute("passwordChangeRequired", false);
		redirectAttributes.addFlashAttribute("infoMessage", "Password changed successfully! Please login");

		request.getSession().invalidate();

		// Delete the JSESSIONID cookie
		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		return "redirect:/login?logout";
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

	@GetMapping("/admin/get-all-users")
	public String showAllUser(Model model) {
		List<User> usersList = userDetailsManager.getAllUsers();
		model.addAttribute("usersList", usersList);
		return "showAllUser";
	}

	@GetMapping("/admin/user-details")
	public String showUserDetails(@RequestParam("userId") Long userId, Model model) {

		User user = userDetailsManager.getUserById(userId);
		model.addAttribute("user", user);
		return "userDetail";
	}

	@GetMapping("/admin/edit-user")
	public String showEditEmployeeForm(@RequestParam("userId") Long userId, Model model) {

		User user = userDetailsManager.getUserById(userId);
		model.addAttribute("user", user);
		return "editUser";
	}

	@PostMapping("/admin/edit-user")
	public String updateUser(@ModelAttribute("user") @Valid User user, @RequestParam List<String> selectedRoles,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			ObjectError error = bindingResult.getGlobalError();
			bindingResult.addError(error);
			model.addAttribute("errorMessage", error);
			return "editUser";
		}
		try {
			updateUser(user, selectedRoles);
			model.addAttribute("successMessage", "User : " + user.getUsername() + " update successfully!");

		} catch (Exception e) {

			model.addAttribute("errorMessage",
					user.getUsername() + "  already exists. Please input another username.!");
			return "editUser";
		}

		return "successfullySavedPage";
	}

	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("username") String username) {
		session.setAttribute("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		model.addAttribute("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

		return "login";
	}

	@GetMapping("/admin/register-role")
	public String showRoleRegistrationForm(Model model) {
		model.addAttribute("roleName", "");
		return "roleRegistration";
	}

	@PostMapping("admin/register-role")
	public String registerRole(@ModelAttribute("roleName") String roleName, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getGlobalError();
			bindingResult.addError(error);
			model.addAttribute("errorMessage", error);
			return "roleRegistration";
		}
		try {
			userDetailsManager.createRole(new Role(roleName));
			model.addAttribute("successMessage", "Role : " + roleName + " registered successfully!");

		} catch (Exception e) {

			model.addAttribute("errorMessage", roleName + "  already exists. Please input another role name.!");
		}

		return "roleRegistration";
	}

	@GetMapping("/admin/register-user")
	public String showRegistrationForm(Model model) {

		List<Long> roles = new ArrayList<>();
		model.addAttribute("selectedRoles", roles);
		model.addAttribute("user", new User());
		return "userRegistration";
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
		// passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(defaultPassword));
		user.setNeedsPasswordChange(true);
		List<Role> selectedRoles = convertRoleIdsToRoles(rolesId, userDetailsManager);
		user.setRoles(selectedRoles);

		userDetailsManager.createUser(user);
	}

	private void updateUser(User user, List<String> rolesName) {
		List<Role> selectedRoles = convertRoleIdsToRoles(rolesName, userDetailsManager);
		user.setRoles(selectedRoles);
		userDetailsManager.createUser(user);
	}

	private void createInitialAdmin() {
		User user = new User();
		user.setUsername("admin");
		user.setAccountNonLocked(true);
		user.setFailedAttemptCount(0);
		/// passwordEncoder = new BCryptPasswordEncoder();

		user.setPassword(passwordEncoder.encode(defaultAdminPassword));
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

	@ModelAttribute("allRoles")
	public List<Role> populateRoles() {
		return userDetailsManager.getAllrole();
	}

// get all role from Enum	
//	@ModelAttribute("allRoles")
//	public Roles[] populateRoles() {
//		return Roles.values();
//	}

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

package com.springboot.jpa.hibernate.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.jpa.hibernate.model.CsvUserDto;
import com.springboot.jpa.hibernate.model.Role;
import com.springboot.jpa.hibernate.model.User;
import com.springboot.jpa.hibernate.respository.IRoleRepository;
import com.springboot.jpa.hibernate.respository.IUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private static final int MAX_FAILED_ATTEMPTS = 3;
	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;

	public MyUserDetailsService(IUserRepository userRepository, IRoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public void createUser(User user) {
		userRepository.save(user); 
	}

	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	public Page<User> getUsers(int page, int size, String sortColumn, String sortDirection) {
		Sort sort = Sort.by(sortColumn);
		if (sortDirection.equals("desc")) {
			sort = sort.descending();
		}
		return userRepository.findAll(PageRequest.of(page, size, sort));
	}

	public User getUserById(Long id) {

		Optional<User> optional = userRepository.findById(id);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException(" User not found for id :: " + id);
		}
		return user;
	}

	public User getUserByusername(String username) {

		Optional<User> optional = userRepository.findByUsername(username);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException("User not found for username :: " + username);
		}
		return user;
	}

	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	public List<Role> getAllrole() {
		return userRepository.findAllRole();
	}

	public Role getRoleById(Long id) {
		return userRepository.findRoleById(id);
	}

	public Role getRoleByName(String roleName) {
		return roleRepository.findByName(roleName);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		if (!user.getAccountNonLocked()) {
			throw new LockedException("This Account is locked!!");
		} else if (user.getFailedAttemptCount() >= MAX_FAILED_ATTEMPTS) {
			lockUserAccount(user);
			throw new LockedException("Account locked due to too many failed login attempts");

		} else {
//			reSetFailedAttemptCount(user);
		}
 
		return new UserPrincipal(user);
	}

	private void lockUserAccount(User user) {
		user.setAccountNonLocked(false);
		userRepository.save(user);
	}

	public void importUsersCSV(MultipartFile file) throws IOException {
		List<CsvUserDto> users = CsvImportUtils.read(CsvUserDto.class, file.getInputStream());
		try {
			userRepository.saveAll(transferCsvToUserData(users));
		} catch (Exception e) {
			Throwable cause = e.getCause();
			throw new RuntimeException(cause);
		}

	}

	public List<User> transferCsvToUserData(List<CsvUserDto> csvUserDtoList) {

		List<User> userList = new ArrayList<User>();
		for (CsvUserDto csvUserDto : csvUserDtoList) {
			User user = new User();
			List<Role> roleList = new ArrayList<Role>();
			user.setId(csvUserDto.getId());
			user.setUsername(csvUserDto.getUsername());
			user.setPassword(csvUserDto.getPassword());
			user.setAccountNonLocked(csvUserDto.isAccountNonLocked());

			List<String> roleNameList = csvUserDto.getRoles();
			for (String rolesString : roleNameList) {

				rolesString = rolesString.trim();
				String[] roleNames = rolesString.split("\\s*,\\s*");

				for (String roleName : roleNames) {
					Role role = roleRepository.findByName(roleName);
					if (role == null) {
						role = new Role();
						role.setName(roleName);
						role = roleRepository.save(role);
					}
					roleList.add(role);
				}

			}

			user.setRoles(roleList);
			user.setFailedAttemptCount(csvUserDto.getFailedAttemptCount());
			user.setNeedsPasswordChange(csvUserDto.isNeedsPasswordChange());
			userList.add(user);
		}
		return userList;

//    	 return csvUserDtoList.stream()
//    	            .map(csvUserDto -> {
//    	                User user = new User();
//    	                user.setId(csvUserDto.getId());
//    	                user.setUsername(csvUserDto.getUsername());
//    	                user.setPassword(csvUserDto.getPassword());
//    	                user.setAccountNonLocked(csvUserDto.isAccountNonLocked()); 
//    	                
//    	                List<Role> roles = csvUserDto.getRoles().stream().map(roleName -> {
//    	                    Role role = roleRepository.findByName(roleName);
//    	                    if (role == null) {
//    	                        role = new Role();
//    	                        role.setName(roleName);
//    	                        role = roleRepository.save(role);
//    	                    }
//    	                    return role;
//    	                }).collect(Collectors.toList());
//    	               
//    	                user.setRoles(roles);
//    	                user.setFailedAttemptCount(csvUserDto.getFailedAttemptCount());
//    	                user.setNeedsPasswordChange(csvUserDto.isNeedsPasswordChange());
//    	                return user;
//    	            })
//    	            .collect(Collectors.toList());
	}
}

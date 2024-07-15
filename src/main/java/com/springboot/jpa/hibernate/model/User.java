package com.springboot.jpa.hibernate.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

//@Transactional
@Component
@Entity
@Table(name = "users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	private String password;
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	private int failedAttemptCount;
	private boolean needsPasswordChange;
	public User() {
	}

	 

	public User(Long id, String username, String password, boolean accountNonLocked, List<Role> roles,
			int failedAttemptCount, boolean needsPasswordChange) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.accountNonLocked = accountNonLocked;
		this.roles = roles;
		this.failedAttemptCount = failedAttemptCount;
		this.needsPasswordChange = needsPasswordChange;
	}



	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> "read");
	}

//	@Override
//	 public Collection<? extends GrantedAuthority> getAuthorities() {
//	  String[] userRoles = getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
//	        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
//	        return authorities;
//	 }
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean setAccountNonLocked(Boolean accountNonLocked) {
		return this.accountNonLocked = accountNonLocked;
	}

	public boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public int getFailedAttemptCount() {
		return failedAttemptCount;
	}

	public void setFailedAttemptCount(int failedAttemptCount) {
		this.failedAttemptCount = failedAttemptCount;
	}

	public boolean isNeedsPasswordChange() {
		return needsPasswordChange;
	}

	public void setNeedsPasswordChange(boolean needsPasswordChange) {
		this.needsPasswordChange = needsPasswordChange;
	}

}
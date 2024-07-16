package com.springboot.jpa.hibernate.model;

import java.util.List;

import com.opencsv.bean.CsvBindByName;
 

public class CsvUserDto {
 
	@CsvBindByName
	private Long id; 
	@CsvBindByName
	private String username;
	@CsvBindByName
	private String password;
	@CsvBindByName 
	private boolean accountNonLocked;  
	@CsvBindByName
	 private List<String> roles;
	@CsvBindByName
	private int failedAttemptCount;
	@CsvBindByName
	private boolean needsPasswordChange;
	
	public CsvUserDto(Long id, String username, String password, boolean accountNonLocked, List<String> roles,
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
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
	public CsvUserDto() {
		super(); 
	}
  
}

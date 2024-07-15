package com.springboot.jpa.hibernate.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.jpa.hibernate.model.User;

public class UserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;

 	private Long id; 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
 
	 public UserPrincipal(User user, Long id) {
	        this.user = user;
	        this.id = id;
	    }
 

	public UserPrincipal(User customUser) {
		  this.user = customUser;
	}
 
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Collections.singleton(new SimpleGrantedAuthority("USER"));
//	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return user.getRoles().stream()
	        .map(role -> new SimpleGrantedAuthority(role.getName()))
	        .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getPassword();
	}
//	 @Override
//	    public boolean isAccountNonExpired() {
//	        return true;
//	    }
//
//	    @Override
//	    public boolean isAccountNonLocked() {
//	        return true;
//	    }
//
//	    @Override
//	    public boolean isCredentialsNonExpired() {
//	        return true;
//	    }
//
//	    @Override
//	    public boolean isEnabled() {
//	        return user.isEnabled();
//	    }
 }

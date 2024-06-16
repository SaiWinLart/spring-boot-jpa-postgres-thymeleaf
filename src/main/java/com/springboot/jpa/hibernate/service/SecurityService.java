package com.springboot.jpa.hibernate.service;
 
public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
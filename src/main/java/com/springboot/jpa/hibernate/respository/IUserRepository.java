package com.springboot.jpa.hibernate.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.jpa.hibernate.model.User;
 
 
@Repository 
public interface IUserRepository extends JpaRepository<User, Long> { 
  

 //User findByUsername(String username); 
 Optional<User> findByUsername(String username);
 

}
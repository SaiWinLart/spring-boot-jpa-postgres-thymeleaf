package com.springboot.jpa.hibernate.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.jpa.hibernate.model.Role;
import com.springboot.jpa.hibernate.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	Page<User> findAll(Pageable pageable);

	Optional<User> findByUsername(String username);

	@Query("SELECT role FROM Role role")
	List<Role> findAllRole();

	@Query("SELECT r FROM Role r WHERE r.id = :id")
	Role findRoleById(@Param("id") Long id);

}
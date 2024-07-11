package com.springboot.jpa.hibernate.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.jpa.hibernate.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

	 Role  findByName(String name);

	@Query("SELECT role FROM Role role")
	List<Role> findAllRole();

	@Query("SELECT r FROM Role r WHERE r.id = :id")
	Role findRoleById(@Param("id") Long id);

}
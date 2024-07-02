package com.springboot.jpa.hibernate.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.jpa.hibernate.model.Employee;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByNameAndId(String name, Long id);

	// @Query("SELECT e FROM Employee e WHERE e.name = :name OR e.id = :id")
	// @Query("SELECT e FROM Employee e WHERE e.id = :id OR e.name LIKE :name%")
	// @Query("SELECT e FROM Employee e WHERE (e.name LIKE :name% OR e.id = :id)")
	@Query("SELECT e FROM Employee e " + "WHERE (:name IS NULL OR e.name LIKE :name%) "
			+ "AND (:id IS NULL OR e.id = :id)")

	List<Employee> findByNameOrId(@Param("name") String name, @Param("id") Long id);

	List<Employee> findAllByNameContainingOrId(String name, Long id);
}
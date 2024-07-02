package com.springboot.jpa.hibernate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity 
@Table(name = "employees" ,uniqueConstraints = @UniqueConstraint(columnNames = {"mm_id"}))
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull(message = "ID cannot be empty.")
	private Long id;
	@NotEmpty(message = "Name cannot be empty.")
	@Size(min = 2, max = 50)
	private String name;
	private String department;
	private String role;
	@NotEmpty(message = "Nrc ID cannot be empty.")
	@Size(min = 17, max = 18)
	@Pattern(regexp = "^([1-9]|1[0-4])/[a-zA-Z]{6}\\([n|e|p]\\)\\d{6}$", message = "Invalid format!  nrc id format should be like : 12/kamaya(n)112233")
	//@Column(unique = true )
	private String mmNrc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMmNrc() {
		return mmNrc;
	}

	public void setMmNrc(String mmNrc) {
		this.mmNrc = mmNrc;
	}

	public Employee(Long id, String name, String department, String role, String mmNrc) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.role = role;
		this.mmNrc = mmNrc;
	}

	public Employee() {
		super(); 
	}

}

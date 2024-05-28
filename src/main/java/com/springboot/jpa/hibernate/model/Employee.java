package com.springboot.jpa.hibernate.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String department;
	private String role;
	// Unidirectional
//	@Column(name="mm_nrc_id")
	// @PrimaryKeyJoinColumn(name = "mm_nrc_id", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "mm_nrc_id", referencedColumnName = "id")
	private MMnrc mmNrc;

	public MMnrc getMmNrc() {
		return mmNrc;
	}

	public void setMmNrc(MMnrc mmNrc) {
		this.mmNrc = mmNrc;
	}

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

	public Employee(String name, String department, String role, MMnrc mmNrc) {
		super();
		this.name = name;
		this.department = department;
		this.role = role;
		this.mmNrc = mmNrc;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

}

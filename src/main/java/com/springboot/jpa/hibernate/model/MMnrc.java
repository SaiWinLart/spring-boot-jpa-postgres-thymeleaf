package com.springboot.jpa.hibernate.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.persistence.Column; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "myanmarnrc")
public class MMnrc {
	@Column(name = "mmId")
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Long id;
	@NotEmpty(message = "Nrc ID cannot be empty.")
	@Size(min = 17, max = 18)
	@Pattern(regexp = "^([1-9]|1[0-4])/[a-zA-Z]{6}\\([n|e|p]\\)\\d{6}$", message = "Invalid format!  nrc id format should be like : 12/kamaya(n)112233")
	private String mmId;
	@NotEmpty(message = "Name cannot be empty.")
	@Size(min = 2, max = 50)
	private String name;
	private int age;
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;
	@NotEmpty(message = "Father Name cannot be empty.")
	private String fatherName;
	@NotEmpty(message = "Mother Name cannot be empty.")
	private String motherName;
	private LocalDate issueDate;
	@NotNull(message = "Date of birth cannot be null")
	private LocalDate dateOfBirth;
	@Enumerated(EnumType.ORDINAL)
	private CityOfBirthPlace cityOfBirthPlace;
	@Enumerated(EnumType.ORDINAL)
	private StateAndDivision stateAndDivision;
	private String addressId;
	private String occupation;
	@Enumerated(EnumType.ORDINAL)
	private BloodType bloodType;

	public MMnrc() {
		super();
	}

	public String getMmId() {
		return mmId;
	}

	public void setMmId(String mmId) {
		this.mmId = mmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public CityOfBirthPlace getCityOfBirthPlace() {
		return cityOfBirthPlace;
	}

	public void setCityOfBirthPlace(CityOfBirthPlace cityOfBirthPlace) {
		this.cityOfBirthPlace = cityOfBirthPlace;
	}

	public StateAndDivision getStateAndDivision() {
		return stateAndDivision;
	}

	public void setStateAndDivision(StateAndDivision stateAndDivision) {
		this.stateAndDivision = stateAndDivision;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public MMnrc(String mmId, String name, int age, Gender gender, String fatherName, String motherName,
			LocalDate issueDate, LocalDate dateOfBirth, CityOfBirthPlace cityOfBirthPlace,
			StateAndDivision stateAndDivision, String addressId, String occupation, BloodType bloodType) {
		super();
		this.mmId = mmId;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.issueDate = issueDate;
		this.dateOfBirth = dateOfBirth;
		this.cityOfBirthPlace = cityOfBirthPlace;
		this.stateAndDivision = stateAndDivision;
		this.addressId = addressId;
		this.occupation = occupation;
		this.bloodType = bloodType;
	}

}

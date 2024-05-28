package com.springboot.jpa.hibernate.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.persistence.Column; 

@Entity
@Table(name = "myanmarnrc") 
public class MMnrc {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mmId;
	private String name;
	private int age;
	private String gender;
	private String fatherName;
	private String motherName;
	private LocalDate issueDate;
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

	public MMnrc(Long id, String mmId, String name, int age, String gender, String fatherName, String motherName,
			LocalDate issueDate, LocalDate dateOfBirth, CityOfBirthPlace cityOfBirthPlace,
			StateAndDivision stateAndDivision, String address, String occupation, BloodType bloodType) {
		this.id = id;
		this.mmId = mmId;
		this.name = name;
		this.age = age;
		this.setGender(gender);
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.issueDate = issueDate;
		this.dateOfBirth = dateOfBirth;
		this.cityOfBirthPlace = cityOfBirthPlace;
		this.stateAndDivision = stateAndDivision;
		this.addressId = address;
		this.occupation = occupation;
		this.bloodType = bloodType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "MMnrc{" + "id='" + id + '\'' + ", mmId='" + mmId + '\'' + ", name='" + name + '\'' + ", age=" + age
				+ ", fatherName='" + fatherName + '\'' + ", motherName='" + motherName + '\'' + ", issueDate="
				+ issueDate + ", dateOfBirth=" + dateOfBirth + ", cityOfBirthPlace=" + cityOfBirthPlace
				+ ", stateAndDivision=" + stateAndDivision + ", address=" + addressId + ", occupation='" + occupation
				+ '\'' + ", bloodType=" + bloodType + '}';
	}

}

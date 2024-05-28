package com.springboot.jpa.hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.jpa.hibernate.model.Employee;

@Service
public interface IEmployeeService {
	String saveEmployee(Employee employee);

	List<Employee> getAllEmployees();

	Employee getEmployeeById(Long id);

	String updateEmployee(Employee employee, Long id);

	String deleteEmployee(Long id);

	String deleteAllEmployees();

}

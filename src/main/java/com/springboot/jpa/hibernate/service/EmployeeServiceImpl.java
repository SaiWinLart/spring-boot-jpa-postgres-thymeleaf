package com.springboot.jpa.hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.jpa.hibernate.model.Employee;
import com.springboot.jpa.hibernate.respository.IEmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	private final IEmployeeRepository employeeRepository;

	public EmployeeServiceImpl(IEmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public String saveEmployee(Employee employee) {
		  employeeRepository.save(employee);
		  return "Data of "+ employee.getName()+" is successfuly saved in database.";
	}

	@Override
	public List<Employee> getAllEmployees() {

		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {

		return employeeRepository.getReferenceById(id);
	}

	@Override
	public String updateEmployee(Employee employee, Long id) {

		if (employeeRepository.existsById(id)) {
			employee.setId(id);
			employeeRepository.save(employee);
			 return "Data of "+ employee.getName()+" is successfuly updated in database.";
		 
		} else {
			return id + " not exist";
		}

	}

	@Override
	public String deleteEmployee(Long id) {

		if (employeeRepository.existsById(id)) {
			employeeRepository.delete(employeeRepository.getReferenceById(id));
			return "The record is deleted.";
		} else {
			return id + " not exist";
		}

	}

	@Override
	public String deleteAllEmployees() {
		if (employeeRepository.count() > 0) {
			employeeRepository.deleteAll();
			return "All data deleted!";
		} else {
			return "Table is empty, no data is deleted. ";
		}

	}

}

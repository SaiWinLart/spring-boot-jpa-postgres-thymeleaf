package com.springboot.jpa.hibernate.service;

import java.util.List;
import java.util.Optional;

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
		return "Data of " + employee.getName() + " is successfuly saved in database.";
	}

	@Override
	public List<Employee> getAllEmployees() {

		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {

		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;
	}

	@Override
	public String updateEmployee(Employee employee, Long id) {

		if (employeeRepository.existsById(id)) {
			employee.setId(id);
			employeeRepository.save(employee);
			return "Data of " + employee.getName() + " is successfuly updated in database.";

		} else {
			return id + " not exist";
		}

	}

	@Override
	public String deleteEmployee(Long id) {

//		if (employeeRepository.existsById(id)) {
//			employeeRepository.delete(employeeRepository.getReferenceById(id));
//			return "The record is deleted.";
//		} else {
//			return id + " not exist";
//		}

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
		employeeRepository.delete(employee);
		return "The record is deleted.";
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

	@Override
	public List<Employee> getEmployeeByNameAndId(String name, Long id) {

		List<Employee> employees = employeeRepository.findByNameAndId(name, id);

		if (employees != null) {
			return employees;
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}

	}

	@Override
	public List<Employee> findByNameOrId(String name, Long id) {

		List<Employee> employees = employeeRepository.findByNameOrId(name, id);

		if (employees.isEmpty()) {

			throw new RuntimeException(" Employee not found for id => " + id);
		} else {
			return employees;
		}

	}

	@Override
	public List<Employee> findAllByNameContainingOrId(String name, Long id) {
		if (id == null) {
			throw new RuntimeException("Id have to input");
		}
		List<Employee> employees = employeeRepository.findAllByNameContainingOrId(name, id);

		if (employees.isEmpty()) {

			throw new RuntimeException(" Employee not found for id :: " + id);
		} else {
			return employees;
		}
	}

}

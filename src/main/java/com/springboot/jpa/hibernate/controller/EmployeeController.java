package com.springboot.jpa.hibernate.controller;

import com.springboot.jpa.hibernate.service.IEmployeeService;

public class EmployeeController {

	private final IEmployeeService employeeService;

	public EmployeeController(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

}

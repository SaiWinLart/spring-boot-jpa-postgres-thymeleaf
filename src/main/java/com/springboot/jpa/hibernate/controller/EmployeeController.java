package com.springboot.jpa.hibernate.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.jpa.hibernate.model.Employee;
import com.springboot.jpa.hibernate.service.IEmployeeService;
import com.springboot.jpa.hibernate.service.IMmnrcService;

import jakarta.validation.Valid;

@Controller
public class EmployeeController {

	private final IEmployeeService employeeService;
	private final IMmnrcService mmNrcService;

	public EmployeeController(IEmployeeService employeeService, IMmnrcService mmNrcService) {
		this.employeeService = employeeService;
		this.mmNrcService = mmNrcService;
	}

	 
	@GetMapping("/home")
	public String viewHomePage(Model model) { 
		return "index";
	}

	@GetMapping("/admin/add-employee")
	public String showAddEmployeeForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "addEmployee";
	}

	@GetMapping("/get-all-employees")
	public String showEmployeeList(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "showAllEmployees";
	}

	@PostMapping("/save-employee")
	public String addEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		String err = "";
		try {

			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", result);
				redirectAttributes.addFlashAttribute("employee", employee);

				return "addEmployee";
			}
			if (!mmNrcService.mmIdExist(employee.getMmNrc())) {
				err = "Invalid Nrc id, can't register employee. Please add new MM NRC.";
				ObjectError error = new ObjectError("globalError", err);
				result.addError(error);
				model.addAttribute("errorMessage", err);
				return "addEmployee";
			} else {

				employeeService.saveEmployee(employee);

				model.addAttribute("employee", employee);

				return "successfullySavedPage";
			}

		} catch (DataIntegrityViolationException exception) {
			result.rejectValue("mmNrc", "error.unique.employee",
					"Nrc ID : " + employee.getMmNrc() + " already exists in this company.");
			return "addEmployee";
		}

	}

	@PostMapping("/delete-employees")
	public String deleteEmployees(@RequestParam("employeeIds") List<Long> employeeIds) {
		if (employeeIds.isEmpty()) {

		} else {
			for (Long empId : employeeIds) {
				employeeService.deleteEmployee(empId);
			}

		}

		return "redirect:/get-all-employees";
	}
}

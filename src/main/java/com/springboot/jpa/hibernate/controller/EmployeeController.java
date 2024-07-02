package com.springboot.jpa.hibernate.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.jpa.hibernate.model.Employee;
import com.springboot.jpa.hibernate.model.MMnrc;
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

	@GetMapping("/employee-details/{employeeId}")
	public String getMMnrcByEmployeeId(@PathVariable Long employeeId, Model model) {

		Employee employee = employeeService.getEmployeeById(employeeId);
		MMnrc mmnrc = mmNrcService.getMMnrcByMMid(employee.getMmNrc());
		model.addAttribute("mmnrc", mmnrc);
		model.addAttribute("employee", employee);
		return "employeeDetails";
	}

	@GetMapping("/admin/edit-employee")
	public String showEditEmployeeForm(@RequestParam("employeeId") Long employeeId, Model model) {

		Employee employee = employeeService.getEmployeeById(employeeId);
		model.addAttribute("employee", employee);
		return "editEmployee";
	}

	@GetMapping("/get-all-employees")
	public String showEmployeeList(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		model.addAttribute("employee", new Employee());
		model.addAttribute("id", "");
		model.addAttribute("name", "");
		return "showAllEmployees";
	}

	@GetMapping("/get-employee")
	public String searchEmployee(@RequestParam(value = "id", required = false) Long employeeId,
			@RequestParam("name") String employeeName, Model model) {
		String err;
		try {
			List<Employee> employees = employeeService.findByNameOrId(employeeName, employeeId);
			Employee employee = new Employee();
			employee.setId(employeeId);
			employee.setName(employeeName);
			model.addAttribute("employee", employee);
			model.addAttribute("employees", employees);
			return "showAllEmployees";

		} catch (Exception e) {
			err = "No Employee match this ID." + e.getMessage();
			// ObjectError error = new ObjectError("globalError", err);
			// result.addError(error);
			model.addAttribute("errorMessage", err);
			Employee employee = new Employee();
			employee.setId(employeeId);
			employee.setName(employeeName);
			model.addAttribute("employee",employee);
			return "showAllEmployees";
		}

	}

//	@ExceptionHandler(MissingServletRequestParameterException.class)
//    public String handleMissingParams(MissingServletRequestParameterException ex, Model model) {
//        String paramName = ex.getParameterName();  
//        model.addAttribute("errorMessage", paramName + " parameter is needed to input for search.");
//        model.addAttribute("employee", new Employee()); 
//        return "showAllEmployees";
//	}
// 

	@PostMapping("/admin/save-employee")
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

	@PostMapping("/admin/update-employee")
	public String updateEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		Employee existingEmployee = employeeService.getEmployeeById(employee.getId());
		String err = "";
		try {

			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", result);
				redirectAttributes.addFlashAttribute("employee", employee);

				return "editEmployee";
			}
			if (!mmNrcService.mmIdExist(employee.getMmNrc())) {
				err = "Invalid Nrc id, can't update employee. Please input valid MM NRC.";
				ObjectError error = new ObjectError("globalError", err);
				result.addError(error);
				model.addAttribute("errorMessage", err);
				return "editEmployee";
			} else {
				existingEmployee.setId(employee.getId());
				existingEmployee.setName(employee.getName());
				existingEmployee.setMmNrc(employee.getMmNrc());
				existingEmployee.setDepartment(employee.getDepartment());
				existingEmployee.setRole(employee.getRole());
				employeeService.saveEmployee(existingEmployee);

				model.addAttribute("employee", existingEmployee);

				return "successfullySavedPage";
			}

		} catch (DataIntegrityViolationException exception) {
			result.rejectValue("mmNrc", "error.unique.employee",
					"Nrc ID : " + employee.getMmNrc() + " already exists in this company.");
			return "editEmployee";
		}

	}

	@PostMapping("/admin/delete-employees")
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

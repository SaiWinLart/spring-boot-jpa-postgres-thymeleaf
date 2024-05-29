package com.springboot.jpa.hibernate.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.jpa.hibernate.model.Employee;
import com.springboot.jpa.hibernate.service.IEmployeeService;

import jakarta.servlet.ServletContext;

@Controller
public class EmployeeController {

	private final IEmployeeService employeeService;
	private final ServletContext servletContext;

	public EmployeeController(IEmployeeService employeeService, ServletContext servletContext) {
		this.employeeService = employeeService;
		this.servletContext = servletContext;
	}

	// display home page
	@GetMapping("/")
	public String viewHomePage(Model model) {
		// model.addAttribute("listEmployees", employeeService.getAllEmployees());
		return "index";
	}

	@GetMapping("/addEmployee")
	public String showAddEmployeeForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "addEmployee";
	}

	@GetMapping("/showAllEmployees")
	public String showEmployeeList(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "showAllEmployees";
	}

	@PostMapping("/addEmployee")
	public String addEmployee(@ModelAttribute("employee") Employee employee,Model model, RedirectAttributes redirectAttributes) {
		// save employee to database
		employeeService.saveEmployee(employee);
 	//redirectAttributes.addFlashAttribute("employee",employee); 
		model.addAttribute("employee",employee);
		return "savedPage";
	}
	@PostMapping("/deleteEmployees")
    public String deleteEmployees(@RequestParam("employeeIds") List<Long> employeeIds) {
		if(employeeIds.isEmpty()) {
			
		}else {
			for(Long empId : employeeIds ) {
				employeeService.deleteEmployee(empId);
			}
			
		}
        
        return "redirect:/showAllEmployees";
    }
}

package com.employee.model_2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model_2.entity.Employee;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@GetMapping
	public String getEmployees() {
		return "List of employees";
	}

	@PostMapping
	public Employee createEmployee() {
		 Employee employee = new Employee();
		 employee.setId(1L);
		 employee.setName("John Doe");
		 employee.setEmail("jone@gmail.com");
		 employee.setDepartment("IT");
		 employee.setDateOfJoining(new java.util.Date());
		 	
		return employee;
	}
	
}

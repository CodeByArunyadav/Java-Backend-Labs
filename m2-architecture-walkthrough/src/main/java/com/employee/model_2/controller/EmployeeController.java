package com.employee.model_2.controller;

import com.employee.model_2.dto.EmployeeDTO;
import com.employee.model_2.entity.EmployeeEntity;
import com.employee.model_2.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	private final EmployeeDTO employeedto;

    public EmployeeController(EmployeeDTO employeedto) {
       this.employeedto = employeedto;
    }

    @GetMapping("/{id}")
	public EmployeeEntity getEmployees(@PathVariable Long id) {

		return employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
	}

	@PostMapping
	public EmployeeEntity createEmployee(@RequestBody EmployeeEntity employee) {
		// Employee employee = new Employee();

		return employeeRepository.save(employee);
	}

	@PutMapping
	public EmployeeEntity updateEmployee( @RequestBody EmployeeEntity employee1)
	{
		return employee1;
	}
}

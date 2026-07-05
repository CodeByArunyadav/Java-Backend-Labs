package com.employee.model_2.controller;

import com.employee.model_2.dto.EmployeeDTO;
import com.employee.model_2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
   	@Autowired
	private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
       this.employeeService=employeeService;
    }

    @GetMapping("/{id}")
	public EmployeeDTO getEmployees(@PathVariable Long id) {

		return employeeService.getEmployee(id);
	}

    @GetMapping
    public List<EmployeeDTO> getAllEmployee()
    {
        return employeeService.getAllEmployee();
    }

	@PostMapping
	public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
		// Employee employee = new Employee();

		return employeeService.createEmployee(employee);
	}

	@PutMapping
	public EmployeeDTO updateEmployee( @RequestBody EmployeeDTO employee1)
	{
		return employee1;
	}
}

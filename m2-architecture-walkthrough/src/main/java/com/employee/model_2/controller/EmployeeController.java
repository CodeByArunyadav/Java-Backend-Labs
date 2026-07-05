package com.employee.model_2.controller;

import com.employee.model_2.dto.EmployeeDTO;
import com.employee.model_2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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

	@PutMapping(path = "/{id}")
	public EmployeeDTO updateEmployee( @RequestBody EmployeeDTO employee, @PathVariable long id)
	{
		return employeeService.updateEmployee(employee,id);
	}

	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable long id)
	{
		if(employeeService.deleteEmployee(id))
			return "Employee Deleted Successfully";
		else
			return "employee Does not Exit";
	}

	@PatchMapping("/{id}")
	public EmployeeDTO patchEmployee(@RequestBody Map<String,Object> updates, @PathVariable long id)
	{
		return employeeService.patchEmployee(updates,id);

	}
}

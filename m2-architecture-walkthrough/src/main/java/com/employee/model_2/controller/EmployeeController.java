package com.employee.model_2.controller;
import com.employee.model_2.dto.EmployeeDTO;
import com.employee.model_2.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployees(@PathVariable Long id) {

        return ResponseEntity.ok(employeeService.getEmployee(id).orElseThrow(() -> new RuntimeException("Employee not found")));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employee) {
        // Employee employee = new Employee();

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employee));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employee, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.updateEmployee(employee, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        if (employeeService.deleteEmployee(id))
            return ResponseEntity.ok("Employee Deleted Successfully");
        else
            return ResponseEntity.ok("Employee Dose not found");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> patchEmployee(@RequestBody Map<String, Object> updates, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.patchEmployee(updates, id));

    }
}

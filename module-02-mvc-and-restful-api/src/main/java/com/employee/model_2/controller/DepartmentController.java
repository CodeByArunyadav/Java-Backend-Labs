package com.employee.model_2.controller;

import com.employee.model_2.dto.DepartmentDTO;
import com.employee.model_2.entity.DepartmentEntity;
import com.employee.model_2.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping(path = "/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentByID(id);
    }

    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartment(id);
        return null;
    }

    @PutMapping(path = "/{id}")
    public DepartmentDTO updateDepartment(@RequestBody DepartmentDTO departmentDTO, @PathVariable Long id) {
        return departmentService.updateDepartment(departmentDTO, id);
    }

    @PatchMapping(path = "/{id}")
    public DepartmentDTO patchDepartmentField(@RequestBody Map<String,Object> updates, Long id){
        departmentService.patchDepartmentField(updates, id);
        return null;
    }
}

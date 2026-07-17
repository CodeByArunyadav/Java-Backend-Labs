package com.employee.model_2.dto;

import com.employee.model_2.entity.EmployeeEntity;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    //private EmployeeEntity hod;
    //private List<EmployeeEntity> employeeList;
}

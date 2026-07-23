package com.employee.model_2.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
//@Table(name = "department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(
            regexp = "^(IT|HR|FINANCE|SALES|MARKETING|OPERATIONS)$",
            message = "Department must be IT, HR, FINANCE, SALES, MARKETING, or OPERATIONS"
    )
    private String name;
    @OneToOne
    @JoinColumn(name = "hod_deparment")
    private EmployeeEntity hod;
    @OneToMany(mappedBy = "employeeDepartmentID")  // when we used mapped by that means this field is not created but Inverse query table
    private List<EmployeeEntity> employeeList;

}

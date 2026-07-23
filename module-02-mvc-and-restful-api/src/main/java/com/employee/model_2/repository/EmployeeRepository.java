package com.employee.model_2.repository;

import com.employee.model_2.entity.DepartmentEntity;
import com.employee.model_2.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {

   boolean existsByEmail(String email);
//Boolean existByEmployeeDepartmentID(DepartmentEntity employeeDepartmentID);

  //  DepartmentEntity findByEmployeeDepartmentID(DepartmentEntity employeeDepartmentID);
}

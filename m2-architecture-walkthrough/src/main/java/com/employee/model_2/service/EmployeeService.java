package com.employee.model_2.service;

import com.employee.model_2.dto.EmployeeDTO;
import com.employee.model_2.entity.EmployeeEntity;
import com.employee.model_2.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {


   private final EmployeeRepository employeeRepository;

   private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper=modelMapper;
    }

    public EmployeeDTO getEmployee(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return modelMapper.map(employee,EmployeeDTO.class);    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        EmployeeEntity employeeEntity=modelMapper.map(employeeDTO,EmployeeEntity.class);
        EmployeeEntity employeeSave=employeeRepository.save(employeeEntity);

        return modelMapper.map(employeeSave,EmployeeDTO.class);

    }

   public List<EmployeeDTO> getAllEmployee() {
   //return modelMapper.map(employeeRepository.findAll(), EmployeeDTO.class);
    return employeeRepository.findAll()
            .stream().map(employee->modelMapper.map(employee,EmployeeDTO.class)).toList();
    }
}

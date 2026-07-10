package com.employee.model_2.service;

import com.employee.model_2.dto.EmployeeDTO;
import com.employee.model_2.entity.EmployeeEntity;
import com.employee.model_2.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployee(Long id) {
        Optional<EmployeeEntity> employee = employeeRepository.findById(id);

        return Optional.ofNullable(modelMapper.map(employee, EmployeeDTO.class));
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity employeeSave = employeeRepository.save(employeeEntity);

        return modelMapper.map(employeeSave, EmployeeDTO.class);

    }

    public List<EmployeeDTO> getAllEmployee() {
        //return modelMapper.map(employeeRepository.findAll(), EmployeeDTO.class);
        return employeeRepository.findAll()
                .stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
    }

    public boolean isExistsByID(long id) {
        return employeeRepository.existsById(id);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, long id) {
        EmployeeEntity employeeEntitySave = modelMapper.map(employeeDTO, EmployeeEntity.class);
        boolean exists = isExistsByID(id);
        if (!exists) return null;
        employeeEntitySave.setId(id);
        return modelMapper.map(employeeRepository.save(employeeEntitySave), EmployeeDTO.class);
    }

    public boolean deleteEmployee(long id) {
        boolean exists = isExistsByID(id);
        if (exists) employeeRepository.deleteById(id);
        return exists;
    }

    public EmployeeDTO patchEmployee(Map<String, Object> updates, long id) {
        boolean exists = isExistsByID(id);
        if (!exists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        updates.forEach((fields, value) -> {
            Field field = ReflectionUtils.findField(EmployeeEntity.class, fields);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);

    }
}

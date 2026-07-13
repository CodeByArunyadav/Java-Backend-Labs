package com.employee.model_2.service;

import com.employee.model_2.dto.EmployeeDTO;
import com.employee.model_2.entity.EmployeeEntity;
import com.employee.model_2.exception.DuplicateResourceException;
import com.employee.model_2.exception.InvalidRequestException;
import com.employee.model_2.repository.EmployeeRepository;
import com.employee.model_2.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployee(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        if (employeeRepository.existsByEmail(employeeEntity.getEmail())){
            throw new DuplicateResourceException("Mail id"+ employeeEntity.getEmail() + " already created");
        }
        EmployeeEntity employeeSave = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeSave, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployee() {
        //return modelMapper.map(employeeRepository.findAll(), EmployeeDTO.class);
        return employeeRepository.findAll()
                .stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
    }

    public void isExistsByID(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invalid Request User not not found");
        }
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, long id) {
        EmployeeEntity employeeEntitySave = modelMapper.map(employeeDTO, EmployeeEntity.class);
        isExistsByID(id);
        employeeEntitySave.setId(id);
        return modelMapper.map(employeeRepository.save(employeeEntitySave), EmployeeDTO.class);
    }

    public void deleteEmployee(long id) {
        isExistsByID(id);
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO patchEmployee(Map<String, Object> updates, long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User record not found"));
        updates.forEach((fields, value) -> {
            Field field = ReflectionUtils.findField(EmployeeEntity.class, fields);
            //assert field != null;
            if (field == null) {
                throw new InvalidRequestException("One or More Filed InValid");
            }
            if ("id".equals(fields)) {
                throw new InvalidRequestException("Invalid Request ID Could not be update");
            }
            Object convertedValue = convertValue(field, value);
            field.setAccessible(true);
            ReflectionUtils.setField(field, employeeEntity, convertedValue);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);

    }

    private Object convertValue(Field field, Object value) {

        if (value == null) {
            return null;
        }

        Class<?> fieldType = field.getType();

        if (fieldType.equals(BigDecimal.class)) {
            return new BigDecimal(value.toString());
        }

        if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.valueOf(value.toString());
        }

        if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.valueOf(value.toString());
        }

        if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.valueOf(value.toString());
        }

        if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return Boolean.valueOf(value.toString());
        }

        if (fieldType.equals(LocalDate.class)) {
            return LocalDate.parse(value.toString());
        }

        if (fieldType.equals(String.class)) {
            return value.toString();
        }

        return value;
    }
}

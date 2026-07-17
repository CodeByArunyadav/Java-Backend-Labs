package com.employee.model_2.service;

import com.employee.model_2.dto.DepartmentDTO;
import com.employee.model_2.entity.DepartmentEntity;
import com.employee.model_2.repository.DepartmentRepository;
import org.hibernate.query.sqm.tree.expression.Conversion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public DepartmentDTO getDepartmentByID(Long id) {
           return modelMapper.map( departmentRepository.findById(id).orElseThrow(),DepartmentDTO.class);

    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
        return modelMapper.map((departmentRepository.save(departmentEntity)), DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long id) {
        boolean isDepartmentExist = departmentRepository.existsById(id);
        if (!isDepartmentExist) throw new RuntimeException("Department Does not Exist");
        DepartmentEntity departmentEntity = departmentRepository.save(modelMapper.map(departmentDTO, DepartmentEntity.class));
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    public void deleteDepartment(long id) {
        departmentRepository.deleteById(id);
    }

    public DepartmentDTO patchDepartmentField(Map<String, Object> updates, Long id) {

        DepartmentEntity departmentEntity= departmentRepository.getById(id);
        updates.forEach((fields,value)-> {
                      Field field= ReflectionUtils.findField(DepartmentEntity.class,fields);
                      field.setAccessible(true);
                      ReflectionUtils.setField(field,departmentEntity,value);

        });

        return modelMapper.map((departmentRepository.save(departmentEntity)),DepartmentDTO.class);

    }
}


package com.employee.model_2.service;

import com.employee.model_2.dto.DepartmentDTO;
import com.employee.model_2.entity.DepartmentEntity;
import com.employee.model_2.exception.ResourceNotFoundException;
import com.employee.model_2.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public DepartmentDTO getDepartmentByID(Long id) {
           return modelMapper.map( departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department not found")),DepartmentDTO.class);

    }


    public List<DepartmentDTO> getAllByDepartment() {
        return departmentRepository.findAll().stream().map(department-> modelMapper.map(department,DepartmentDTO.class)).toList();
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
      if(!departmentRepository.existsById(id)) {throw new ResourceNotFoundException("Resources not found for ID" + id);}

        DepartmentEntity departmentEntity= departmentRepository.getReferenceById(id);

        updates.forEach((fields,values)->{
            Field field= ReflectionUtils.findField(DepartmentEntity.class,fields);

            if(field==null || field.equals(id)) {throw new ResourceNotFoundException("Field does not exist or Id Could not change");}
            field.setAccessible(true);
            ReflectionUtils.setField(field,departmentEntity,values);

        });

        return modelMapper.map((departmentRepository.save(departmentEntity)),DepartmentDTO.class);

    }

}


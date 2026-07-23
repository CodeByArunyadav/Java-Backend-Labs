package com.codebyarunyadav.HMS.service;

import com.codebyarunyadav.HMS.dto.PatientDTO;
import com.codebyarunyadav.HMS.entity.PatientEntity;
import com.codebyarunyadav.HMS.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import tools.jackson.databind.cfg.MapperBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatientServiceImp implements PatientService {
private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientDTO> getPatient() {
        return patientRepository.findAll()
                .stream()
                .map((patientEntity)->modelMapper.map(patientEntity,PatientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO getPatientByID(Long id) {
        return modelMapper.map(patientRepository.findById(id),PatientDTO.class);

    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {

        PatientEntity patientSaveEntity=patientRepository.save(modelMapper.map(patientDTO, PatientEntity.class));
        return modelMapper.map(patientSaveEntity,PatientDTO.class);
    }

    @Override
    public String deletePatientByID(Long id) {
        return "";
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO, long id) {

        PatientEntity patientEntitySave=modelMapper.map(patientDTO,PatientEntity.class);

        return modelMapper.map(patientRepository.save(patientEntitySave),PatientDTO.class);
    }

    @Override
    public PatientDTO  patchPatient(Map<String, Object> updates, long id) {
        if (!patientRepository.existsById(id)){throw  new RuntimeException();}
       PatientEntity patientEntityData=patientRepository.findById(id).orElseThrow(()->new RuntimeException("No record Found"+id));
        updates.forEach((fields, value )-> {
            Field field=ReflectionUtils.findField(PatientEntity.class,fields);
            if(field == null){throw  new RuntimeException("No Field Match");}
            field.setAccessible(true);
            ReflectionUtils.setField(field,patientEntityData,value);
        });

        return modelMapper.map(patientRepository.save(patientEntityData),PatientDTO.class);
    }
}

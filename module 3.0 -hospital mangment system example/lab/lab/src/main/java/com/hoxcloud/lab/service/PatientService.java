package com.codebyarunyadav.HMS.service;

import com.codebyarunyadav.HMS.dto.PatientDTO;

import java.util.List;
import java.util.Map;

public interface PatientService {
List<PatientDTO> getPatient();
PatientDTO getPatientByID(Long id);
PatientDTO createPatient(PatientDTO patientDTO);
String deletePatientByID(Long id);
PatientDTO updatePatient(PatientDTO patientDTO, long id);
PatientDTO patchPatient(Map<String, Object> updates, long id);
}

package com.codebyarunyadav.HMS.cotroller;

import com.codebyarunyadav.HMS.dto.PatientDTO;
import com.codebyarunyadav.HMS.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping()
    List<PatientDTO> getPatient()
    {
        return patientService.getPatient();
    }

    @GetMapping(path="/{id}")
    PatientDTO getPatientByID(@PathVariable Long id)
    {
        return patientService.getPatientByID(id);
    }

    @PostMapping
    PatientDTO createPatient(@RequestBody PatientDTO patientDTO)
    {
        return patientService.createPatient(patientDTO);
    }

    @PutMapping(path = "/{id}")
    PatientDTO updatePatient(@RequestBody(required = false) PatientDTO patientDTO, @PathVariable Long id)
    {
        return patientService.updatePatient(patientDTO,id);
    }

    @PatchMapping("/{id}")
    public PatientDTO patchPatient(@RequestBody Map<String, Object> updates,
                                   @PathVariable long id) {
        return patientService.patchPatient(updates, id);

    }
}

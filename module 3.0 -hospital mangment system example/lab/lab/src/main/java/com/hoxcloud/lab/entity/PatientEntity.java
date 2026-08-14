package com.codebyarunyadav.HMS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Patient_tbl")
@Getter
@Setter
@RequiredArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String gender;
    private Date dob;
    private String email;
    private String bloodGroup;
    @OneToOne()  //Own Relation
    @JoinColumn(name = "insurance")
    private InsuranceEntity insuranceEntity;

    @OneToMany(mappedBy = "patientEntity")  // inverse side for appointments
    private List<AppointmentEntity> appointments;
}

package com.codebyarunyadav.HMS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
}

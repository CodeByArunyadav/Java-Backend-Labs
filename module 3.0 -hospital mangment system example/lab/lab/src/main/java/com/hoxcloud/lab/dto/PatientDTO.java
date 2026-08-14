package com.codebyarunyadav.HMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PatientDTO {
    private long id;
    private String name;
    private String gender;
    private Date dob;
    private String email;
    private String bloodGroup;
}

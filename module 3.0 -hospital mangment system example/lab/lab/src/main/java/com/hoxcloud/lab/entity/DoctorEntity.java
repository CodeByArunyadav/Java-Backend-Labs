package com.codebyarunyadav.HMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "Doctor_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 255, nullable = false)
    private String name;
    private String specialization;
    @Column(unique = true)
    private String email;
    private Date joinDate;
    @OneToMany(mappedBy = "doctorEntity") //inverse side in relation
    private Set<AppointmentEntity> appointment = new HashSet<AppointmentEntity>() ;
    @ManyToMany
    private Set<DepartmentEntity>doctor= new HashSet<>();

}

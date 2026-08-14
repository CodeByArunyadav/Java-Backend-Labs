package com.hoxcloud.lab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Department_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "department_name",unique = true,nullable = false)
    private String name;
    private String specialization;
    private String email;
    private Date joiningDated;
    @OneToOne()
    @JoinColumn(name = "HOD_Doctor")
    private DoctorEntity hodDoctor;/*
    @ManyToMany
    private Set<DoctorEntity> doctorsByDepartment=new HashSet<DoctorEntity>();
*/
}

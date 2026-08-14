package com.hoxcloud.lab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Entity
@Table(name = "Insurance_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String policyNumber;
    private String provider;
    private Date validUntil;
    @CurrentTimestamp
    private  Date createdAt;
    @OneToOne(mappedBy ="insuranceEntity" )// Inverse Relation
    private PatientEntity patientEntity;

}


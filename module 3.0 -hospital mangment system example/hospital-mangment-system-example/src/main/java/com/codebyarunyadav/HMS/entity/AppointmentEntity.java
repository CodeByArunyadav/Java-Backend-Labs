package com.codebyarunyadav.HMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "Appointment_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AppointmentEntity {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date appointmentDate;
    private String reason;
    private String status;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patientEntity;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctorEntity;

}

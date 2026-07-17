package com.employee.model_2.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="Employee")

public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private String email;
	private String department;
    private BigDecimal salary;
	@CreationTimestamp
	private Date dateOfJoining;
    @Column(name = "isActive")
    private boolean isActive;


}

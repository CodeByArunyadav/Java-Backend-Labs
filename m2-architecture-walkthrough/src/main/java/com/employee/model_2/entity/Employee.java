package com.employee.model_2.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
	Long id;
	String name;
	String email;
	String department;
	Date dateOfJoining;

}

package com.hoxcloud.string;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Employee {
    int id;
    String name;
    String department;
    int salary;
    List<Project> project;

    // 🌟 THE FIX: You must override toString() to control what prints!
    @Override
    public String toString() {
        return "Employee{name='" + name + " "+"Salary : "+ salary+"'}";
    }
}

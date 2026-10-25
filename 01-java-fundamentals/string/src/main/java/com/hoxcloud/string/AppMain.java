package com.hoxcloud.string;

import java.util.*;
import java.util.stream.Collectors;

public class AppMain {

    public static void main(String[] args) {
        // Create 5 projects
        Project p1 = new Project(101, "Banking");
        Project p2 = new Project(102, "E-Commerce");
        Project p3 = new Project(103, "Insurance");
        Project p4 = new Project(104, "HR Portal");
        Project p5 = new Project(105, "Payment Gateway");

        // Create an ArrayList to store employees
        List<Employee> employees = new ArrayList<>();

        // Insert 10 employee records
        employees.add(new Employee(1, "Amit", "IT", 80000,
                Arrays.asList(p1, p2)));

        employees.add(new Employee(2, "Priya", "HR", 55000,
                Arrays.asList(p4)));

        employees.add(new Employee(3, "Rahul", "Finance", 70000,
                Arrays.asList(p1, p5)));

        employees.add(new Employee(4, "Neha", "IT", 90000,
                Arrays.asList(p2, p3)));

        employees.add(new Employee(5, "Vikas", "Development", 95000,
                Arrays.asList(p1, p3, p5)));

        employees.add(new Employee(6, "Sneha", "Testing", 60000,
                Arrays.asList(p2, p4)));

        employees.add(new Employee(7, "Rohit", "IT", 75000,
                Arrays.asList(p3)));

        employees.add(new Employee(8, "Pooja", "Development", 85000,
                Arrays.asList(p1, p2, p4)));

        employees.add(new Employee(11, "Pooja", "Development", 95000,
                Arrays.asList(p1, p2, p4)));

        employees.add(new Employee(9, "Karan", "Finance", 65000,
                Arrays.asList(p5)));

        employees.add(new Employee(10, "Anjali", "Testing", 72000,
                Arrays.asList(p3, p4, p5)));

        /*// 🌟 THE FIX: You must override toString() to control what prints!
        @Override
        public String toString() {
            return "Employee{name='" + name + "'}";
        }*/

        // Display all employees
        //employees.forEach(e-> System.out.println(e.getName()+"  "+e.salary));

        /*employees.stream()
                .sorted(Comparator.comparing((Employee e) -> e.salary).reversed())
                //.max(Comparator.comparing(e->e.salary))
                .skip(1)
                .findFirst()
                .ifPresent(e-> System.out.println(e.getName()+"  "+e.getSalary()));*/

        //emp.forEach(e-> System.out.println(e.getName()+"  "+e.getSalary()));

        // Q1. Find employees whose department is Development.

      /*  employees.stream()
                .sorted(Comparator.comparing((Employee e)->e.salary).reversed())
                .skip(2)
                .findFirst()
                .ifPresent(e-> System.out.println(e.salary));*/
                //.collect(Collectors.toList()).forEach(e-> System.out.println(e.salary+" "+e.getName()));

          /*  Set<Employee> emp=employees.stream().filter(e->e.department.equals("Development"))
           .collect(Collectors.toSet());
           emp.forEach(e-> System.out.println(e.getName()+" "+e.salary));*/

        //2. Find employees whose salary is greater than ₹80,000.

       /* employees.stream().filter(e->e.salary<80000)
                .collect(Collectors.toList()).forEach(e-> System.out.println(e.getName()+""+e.salary));
*/

    //3. Find the employee with the highest salary.

      /*  employees.stream()
                .min(Comparator.comparing(e->e.salary))
                .ifPresent(e-> System.out.println(e.salary));*/
    //4. Find the second-highest distinct salary.
/*
        employees.stream()
                .sorted(Comparator.comparing((Employee e)->e.salary).reversed())
                .distinct()
                .skip(2)
                .findFirst()
                .ifPresent(e-> System.out.println(e.salary+ " "+ e.getName()));*/

        //5. Find the employee earning the second-highest distinct salary.
        /*employees.stream()
                .sorted(Comparator.comparing((Employee e)->e.salary ).reversed())
                .distinct()
                .skip(1)
                .findFirst()
                .ifPresent(e-> System.out.println(e.getName()));*/

        //6. Sort employees by salary in ascending and descending order.
/*
        List<Employee> emp=employees.stream()
                .sorted(Comparator.comparing((Employee e)->e.salary))
                .filter(e->e.salary>80000)
                .collect(Collectors.toList());
        emp.forEach(e-> System.out.println(e.getName()+" " +e.salary));*/

        //7. Group employees by department using groupingBy().
       /* Map<String,List<Employee>> emp=employees.stream()
                .collect(Collectors.groupingBy(e->e.department));

        System.out.println(emp);*/

/*
       Map<String,List<Employee>> emp= employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        // 3. Print the grouped results
        emp.forEach((department, empList) -> {
            System.out.println(department + " : " + empList);
        });*/

        //8. Count the number of employees in each department.

      /*  Map<String,Long>emp=employees.stream()
                .collect(Collectors.groupingBy(e->e.department, Collectors.counting()));

        emp.forEach((e,n)-> System.out.println(e+" : "+n));*/

        //9. Calculate the average salary of employees.

       /* double avgSalary=employees.stream()
                .collect( Collectors.averagingDouble(e->e.salary));
        System.out.println(avgSalary);*/
        //10. Find the highest-paid employee in each department.
        /*Map <String,Optional<Employee>>depWithMaxSal=employees.stream()
                .collect(Collectors.groupingBy(e->e.department,Collectors.maxBy(Comparator.comparingDouble(e->e.salary))));

        System.out.println(depWithMaxSal);
*/
        //11. Find duplicate elements in a list.
        Set<String> seen= new HashSet<>();
       Map<String,List<Employee>>empGroup= employees.stream()
                 .collect(Collectors.groupingBy(e->e.department));


        System.out.println(empGroup);

}

}

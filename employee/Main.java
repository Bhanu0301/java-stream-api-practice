import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        // Employee Stream Practice
        List<Employee> employeeList = Arrays.asList(
                new Employee(1,"Bhanu","IT",40000),
                new Employee(2,"Kalyan","IT",35000),
                new Employee(3,"Suraj","Insurance",30000),
                new Employee(4,"Vamshi","Consulting", 50000)
        );

        //1.Gruop by department : groupingBy
        Map<String, List<Employee>> employeesByDepartment = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDept));

        //Print
        employeesByDepartment.forEach((department,employees)->{
            System.out.println("Department :" + department);
            employees.forEach(System.out::println);
        });

        //2.Filter out employees with salary greater than 35,000
        Map<Boolean, List<Employee>> partitionBySalary = employeeList.stream()
                .collect(Collectors.partitioningBy(employee->employee.getSalary()>35000));
        System.out.println("Employees with salary greater than 35000 : ");
        partitionBySalary.get(true)
                .forEach(System.out::println);
    }
}



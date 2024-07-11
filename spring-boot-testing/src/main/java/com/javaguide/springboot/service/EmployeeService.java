package com.javaguide.springboot.service;

import com.javaguide.springboot.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeByID(long id);
    Employee updateEmployee(Employee updateEmployee);
    void deleteEmployee(long id);
}

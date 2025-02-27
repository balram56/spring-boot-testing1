package com.javaguide.springboot.service.impl;

import com.javaguide.springboot.model.Employee;
import com.javaguide.springboot.repository.EmployeeRepository;
import com.javaguide.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()){
            throw new RuntimeException("Employee already exist with given email:" + employee.getEmail());
        }

       return employeeRepository.save(employee);

    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeByID(long id) {

        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updateEmployee) {

        return employeeRepository.save(updateEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}

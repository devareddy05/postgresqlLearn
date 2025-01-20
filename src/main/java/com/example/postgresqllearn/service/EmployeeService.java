package com.example.postgresqllearn.service;


import com.example.postgresqllearn.Mapper.EmployeeMapper;
import com.example.postgresqllearn.dto.EmployeeDto;
import com.example.postgresqllearn.entity.Employee;
import com.example.postgresqllearn.exception.ResourceNotFoundException;
import com.example.postgresqllearn.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;



    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        return EmployeeMapper.mapToEmployeeDto(employee);

    }



    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for(Employee employee : employees) {
            employeeDtos.add(EmployeeMapper.mapToEmployeeDto(employee));
        }
        return employeeDtos;
    }


    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
       Employee employee= employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
       employee.setEmail(employeeDto.getEmail());
       employee.setFirstName(employeeDto.getFirstName());
       employee.setLastName(employeeDto.getLastName());
       employeeRepository.save(employee);
       return EmployeeMapper.mapToEmployeeDto(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employeeRepository.delete(employee);
    }









}

package com.example.postgresqllearn.service;

import com.example.postgresqllearn.Mapper.EmployeeMapper;
import com.example.postgresqllearn.dto.EmployeeDto;
import com.example.postgresqllearn.entity.Employee;
import com.example.postgresqllearn.exception.DuplicateDataException;
import com.example.postgresqllearn.exception.ResourceNotFoundException;
import com.example.postgresqllearn.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        logger.info("Creating a new employee with email: {}", employeeDto.getEmail());
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        try {
            employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            logger.error("Email already exists: {}", employeeDto.getEmail());
            throw new DuplicateDataException("Email ID " + employeeDto.getEmail() + " already exists.");
        }
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    public EmployeeDto getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    public List<EmployeeDto> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDtos.add(EmployeeMapper.mapToEmployeeDto(employee));
        }
        return employeeDtos;
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        logger.info("Updating employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        try {
            employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            logger.error("Email already exists: {}", employeeDto.getEmail());
            throw new DuplicateDataException("Email ID " + employeeDto.getEmail() + " already exists.");
        }
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employeeRepository.delete(employee);
    }
}

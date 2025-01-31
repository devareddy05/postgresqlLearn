package com.example.postgresqllearn.service;

import com.example.postgresqllearn.Mapper.EmployeeMapper;
import com.example.postgresqllearn.dto.EmployeeDto;
import com.example.postgresqllearn.entity.Employee;
import com.example.postgresqllearn.entity.Meeting;
import com.example.postgresqllearn.exception.DatabaseIntegrityException;
import com.example.postgresqllearn.exception.NoSuchElementException;
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



    @Autowired
    private EmployeeRepository employeeRepository;



    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        employee.setDepartment(employeeDto.getDepartment());
        return saveEmployee(employee);
    }

    public EmployeeDto getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        return employeeMapper.mapToEmployeeDto(employee);
    }

    public List<EmployeeDto> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDtos.add(employeeMapper.mapToEmployeeDto(employee));
        }
        return employeeDtos;
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {


        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setDepartment(employeeDto.getDepartment());
        return saveEmployee(employee);
    }

    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employeeRepository.delete(employee);
    }


    public EmployeeDto saveEmployee(Employee employee) {

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DatabaseIntegrityException("Email ID " + employee.getEmail() + " already exists.");
        }


        if (employeeRepository.existsByPublicId(employee.getPublicId())) {
            throw new DatabaseIntegrityException("This Id " + employee.getPublicId() + " already exists.");
        }


        employeeRepository.save(employee);
        return employeeMapper.mapToEmployeeDto(employee);
    }




    public List<Meeting> getMeetingsByEmployeeId(Long employeeId) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + employeeId));
            return employee.getMeetings();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching meetings for employee ID: " + employeeId, e);
        }
    }


    // ASK BHARATH ABOUT BEST PRACTISE
   /* public EmployeeDto saveEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
        } catch (DatabaseIntegrityException e) {

            throw new DatabaseIntegrityException("Email ID " + employee.getEmail() + " already exists.");

        }

        return employeeMapper.mapToEmployeeDto(employee);
    }*/
}

package com.example.postgresqllearn.Mapper;

import com.example.postgresqllearn.dto.EmployeeDto;
import com.example.postgresqllearn.entity.Employee;
import org.mapstruct.Mapper;


public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee)
    {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto)
    {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
}

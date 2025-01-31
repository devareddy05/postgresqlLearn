package com.example.postgresqllearn.Mapper;

import com.example.postgresqllearn.dto.EmployeeDto;
import com.example.postgresqllearn.entity.Employee;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto  mapToEmployeeDto(Employee employee) ;
    Employee mapToEmployee(EmployeeDto employeeDto);

}
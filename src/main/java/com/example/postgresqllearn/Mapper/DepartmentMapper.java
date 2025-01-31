package com.example.postgresqllearn.Mapper;

import com.example.postgresqllearn.dto.DepartmentDto;
import com.example.postgresqllearn.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto mapToDepartmentDto(Department department);
    Department mapToDepartment(DepartmentDto departmentDto);
}

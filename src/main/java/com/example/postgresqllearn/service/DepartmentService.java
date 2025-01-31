package com.example.postgresqllearn.service;

import com.example.postgresqllearn.Mapper.DepartmentMapper;
import com.example.postgresqllearn.dto.DepartmentDto;
import com.example.postgresqllearn.entity.Department;
import com.example.postgresqllearn.exception.DatabaseIntegrityException;
import com.example.postgresqllearn.exception.ResourceNotFoundException;
import com.example.postgresqllearn.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = departmentMapper.mapToDepartment(departmentDto);
        return saveDepartment(department);
    }

    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        return departmentMapper.mapToDepartmentDto(department);
    }

    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentDtos.add(departmentMapper.mapToDepartmentDto(department));
        }
        return departmentDtos;
    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        department.setName(departmentDto.getName());
        department.setLocation(departmentDto.getLocation());
        department.setManager(departmentDto.getManager());
        return saveDepartment(department);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        departmentRepository.delete(department);
    }

    public DepartmentDto saveDepartment(Department department) {
        try {
            departmentRepository.save(department);
        } catch (DatabaseIntegrityException e) {
            throw new DatabaseIntegrityException("Department name " + department.getName() + " already exists.");
        }
        return departmentMapper.mapToDepartmentDto(department);
    }
}

package com.example.postgresqllearn.repository;

import com.example.postgresqllearn.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

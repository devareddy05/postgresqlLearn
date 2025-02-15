package com.example.postgresqllearn.repository;

import com.example.postgresqllearn.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByPublicId(String publicId);

}

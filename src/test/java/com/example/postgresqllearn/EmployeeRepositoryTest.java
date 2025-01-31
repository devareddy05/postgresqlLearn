package com.example.postgresqllearn;

import com.example.postgresqllearn.entity.Employee;
import com.example.postgresqllearn.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setFirstName("deva");
        employee.setLastName("pennabadi");
        employee.setEmail("devendra@gmail.com");
        employeeRepository.save(employee);
    }

    @Test
    public void testSaveEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
    }

    @Test
    public void testFindEmployeeById() {
        Optional<Employee> fetchedEmployee = employeeRepository.findById(employee.getId());

        assertThat(fetchedEmployee).isPresent();
        assertThat(fetchedEmployee.get().getFirstName()).isEqualTo("deva");
        assertThat(fetchedEmployee.get().getLastName()).isEqualTo("pennabadi");
    }

    @Test
    public void testFindAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotEmpty();
    }

    @Test
    public void testUpdateEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        savedEmployee.setFirstName("devendra");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        assertThat(updatedEmployee.getFirstName()).isEqualTo("devendra");
    }

    @Test
    public void testDeleteEmployee() {
        employeeRepository.delete(employee);
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isEmpty();
    }
}

package com.example.postgresqllearn.service;

import com.example.postgresqllearn.Mapper.EmployeeMapper;
import com.example.postgresqllearn.dto.EmployeeDto;
import com.example.postgresqllearn.entity.Employee;
import com.example.postgresqllearn.exception.DatabaseIntegrityException;
import com.example.postgresqllearn.exception.ResourceNotFoundException;
import com.example.postgresqllearn.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "Devendra Reddy", "Pennabadi", "devendra@gmail.com");
        employeeDto = new EmployeeDto(1L, "Devendra Reddy", "Pennabadi", "devendra@gmail.com");
    }

    @Test
    void createEmployeeExistTest() {
        when(employeeMapper.mapToEmployee(employeeDto)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.mapToEmployeeDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.createEmployee(employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getId(), result.getId());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getEmployeeByIdExistTest() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.mapToEmployeeDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(employeeDto.getId(), result.getId());

    }

    @Test
    void getEmployeeByIdNotExistTest() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getAllEmployeesExistTest() {
        List<Employee> employees = Arrays.asList(employee);
        List<EmployeeDto> employeeDtos = Arrays.asList(employeeDto);

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.mapToEmployeeDto(employee)).thenReturn(employeeDto);

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void updateEmployeeExistTest() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.mapToEmployee(employeeDto)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.mapToEmployeeDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.updateEmployee(1L, employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getEmail(), result.getEmail());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void updateEmployeeNotExistTest() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(1L, employeeDto));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void deleteEmployeeExistTest() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void deleteEmployeeNotExistTest() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void saveEmployeeWithDatabaseIntegrityExceptionTest() {
        when(employeeRepository.save(employee)).thenThrow(new DatabaseIntegrityException("Email already exists."));

        assertThrows(DatabaseIntegrityException.class, () -> employeeService.saveEmployee(employee));
        verify(employeeRepository, times(1)).save(employee);
    }
}

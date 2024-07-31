package com.example.cruddemo.service;

import com.example.cruddemo.dao.EmployeeRepository;
import com.example.cruddemo.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Doe", "john.doe@example.com"));
        employees.add(new Employee("Jane", "Doe", "jane.doe@example.com"));

        when(employeeRepository.findAll()).thenReturn(employees);

        // When
        List<Employee> result = employeeService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Given
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        employee.setId(1);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        // When
        Employee result = employeeService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> employeeService.findById(1));
        assertEquals("Did not find employee id - 1", thrown.getMessage());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        // Given
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        employee.setId(1);
        when(employeeRepository.save(employee)).thenReturn(employee);

        // When
        Employee result = employeeService.save(employee);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John", result.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testDeleteById() {
        // Given
        int employeeId = 1;

        // When
        employeeService.deleteById(employeeId);

        // Then
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}

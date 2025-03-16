package org.assignment.service.impl;

import org.assignment.dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilServiceImplTest {

    @InjectMocks
    private UtilServiceImpl utilService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findEmployeesWithMoreThan4ManagersReturnsCorrectEmployees() {
        Map<String, Employee> employeeMap = new HashMap<>();
        Employee emp1 = new Employee("1", "John", "Doe", 200, "2");
        Employee emp2 = new Employee("2", "Jane", "Smith", 300, "3");
        Employee emp3 = new Employee("3", "Bob", "Brown", 300, "4");
        Employee emp4 = new Employee("4", "Alice", "White", 500, "5");
        Employee emp5 = new Employee("5", "Tom", "Black", 600, "6");
        Employee emp6 = new Employee("6", "Tom", "Senior", 600, "");
        employeeMap.put("1", emp1);
        employeeMap.put("2", emp2);
        employeeMap.put("3", emp3);
        employeeMap.put("4", emp4);
        employeeMap.put("5", emp5);
        employeeMap.put("6", emp6);

        List<Employee> result = utilService.findEmployeesWithMoreThan4Managers(employeeMap);

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void findManagersWithLessSalaryReturnsCorrectManagers() {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<String>> hierarchyMap = new HashMap<>();
        Employee manager = new Employee("1", "John", "Doe", 50, "");
        Employee reportee1 = new Employee("2", "Jane", "Smith", 100, "1");
        Employee reportee2 = new Employee("3", "Bob", "Brown", 100, "1");
        employeeMap.put("1", manager);
        employeeMap.put("2", reportee1);
        employeeMap.put("3", reportee2);
        hierarchyMap.put("1", Arrays.asList("2", "3"));

        List<Employee> result = utilService.findManagersWithLessSalary(hierarchyMap, employeeMap);

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void findManagersWithMoreSalaryReturnsCorrectManagers() {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<String>> hierarchyMap = new HashMap<>();
        Employee manager = new Employee("1", "John", "Doe", 500, "");
        Employee reportee1 = new Employee("2", "Jane", "Smith", 100, "1");
        Employee reportee2 = new Employee("3", "Bob", "Brown", 200, "1");
        employeeMap.put("1", manager);
        employeeMap.put("2", reportee1);
        employeeMap.put("3", reportee2);
        hierarchyMap.put("1", Arrays.asList("2", "3"));

        List<Employee> result = utilService.findManagersWithMoreSalary(hierarchyMap, employeeMap);

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

}

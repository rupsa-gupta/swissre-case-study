package org.assignment.service;

import org.assignment.dto.Employee;

import java.util.List;
import java.util.Map;

public interface UtilService {

    // Method to find employees with more than 4 managers in the chain
    List<Employee> findEmployeesWithMoreThan4Managers(Map<String, Employee> employeeMap);

    List<Employee> findManagersWithLessSalary(Map<String, List<String>> hierarchyMap, Map<String, Employee> employeeMap);

    List<Employee> findManagersWithMoreSalary(Map<String, List<String>> hierarchyMap, Map<String, Employee> employeeMap);
}

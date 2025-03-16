package org.assignment.service.impl;

import org.assignment.dto.Employee;
import org.assignment.service.UtilService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UtilServiceImpl implements UtilService {


    /**
     * Method to find employees with more than 4 managers in the chain
     * @param employeeMap holds employee id and employee object
     * @return list of employees with more than 4 managers
     */
    @Override
    public List<Employee> findEmployeesWithMoreThan4Managers(Map<String, Employee> employeeMap) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employeeMap.values()) {
            int managerCount = countManagers(employee, employeeMap);
            if (managerCount > 4) {
                result.add(employee);
            }
        }
        return result;
    }

    /**
     * Recursive method to count the chain of managers for an employee
     * @param employee employee object
     * @param employeeMap holds employee id and employee object
     * @return count of managers
     */

    private int countManagers(Employee employee, Map<String, Employee> employeeMap) {
        int count = 0;
        String managerId = employee.getManagerId();

        while (managerId != null) {
            Employee manager = employeeMap.get(managerId);
            if (manager != null) {
                count++;
                managerId = manager.getManagerId();
            } else {
                break;
            }
        }

        return count;
    }

    /**
     * Method to find managers with less salary than average of their reportees
     * @param hierarchyMap holds manager id and list of reportee ids
     * @param employeeMap holds employee id and employee object
     * @return list of managers with less than 20% salary than average of their reportees
     */
    @Override
    public List<Employee> findManagersWithLessSalary(Map<String, List<String>> hierarchyMap, Map<String, Employee> employeeMap) {
        List<Employee> result = new ArrayList<>();
        for (String managerId : hierarchyMap.keySet()) {
            double avgSal = getReporteeAvgSalary(hierarchyMap, employeeMap, managerId);
            if (employeeMap.get(managerId).getSalary() < avgSal + (avgSal/5)) {
                result.add(employeeMap.get(managerId));
            }
        }
        return result;
    }

    /**
     * Method to find managers with more salary than average of their reportees
     * @param hierarchyMap holds manager id and list of reportee ids
     * @param employeeMap holds employee id and employee object
     * @return list of managers with more than 50% salary than average of their reportees
     */
 @Override
    public List<Employee> findManagersWithMoreSalary(Map<String, List<String>> hierarchyMap, Map<String, Employee> employeeMap) {
        List<Employee> result = new ArrayList<>();
        for (String managerId : hierarchyMap.keySet()) {
            double avgSal = getReporteeAvgSalary(hierarchyMap, employeeMap, managerId);
            if (employeeMap.get(managerId).getSalary() > avgSal + (avgSal/2)) {
                result.add(employeeMap.get(managerId));
            }
        }
        return result;
    }

    /**
     * Get average salary of reportees for a manager
     * @param hierarchyMap holds manager id and list of reportee ids
     * @param employeeMap holds employee id and employee object
     * @param managerId id of the manager
     * @return average salary of reportees
     */
    private static double getReporteeAvgSalary(Map<String, List<String>> hierarchyMap, Map<String, Employee> employeeMap, String managerId) {
        OptionalDouble avgSalOptional = hierarchyMap.get(managerId).stream().mapToDouble(e -> employeeMap.get(e).getSalary()).average();
        return avgSalOptional.isPresent() ? avgSalOptional.getAsDouble() : 0;
    }

}

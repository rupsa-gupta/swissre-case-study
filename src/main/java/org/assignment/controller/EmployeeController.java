package org.assignment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.assignment.dto.Employee;
import org.assignment.exception.ReadCSVException;
import org.assignment.model.EmployeeRequest;
import org.assignment.model.EmployeeResponse;
import org.assignment.service.ReaderService;
import org.assignment.service.UtilService;
import org.assignment.util.CommonConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final ReaderService readerService;
    private final UtilService utilService;

    @GetMapping("/getRequiredEmployees")
    public EmployeeResponse getEmployeeList(@Valid @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = new EmployeeResponse();
        try {
            Map<String, Employee> employeeMap = new HashMap<>();
            Map<String, List<String>> hierarchyMap = new HashMap<>();
            readerService.readData(employeeRequest.getPathToFile(), employeeMap, hierarchyMap);
            List<Employee> employeesWithMoreManagers = utilService.findEmployeesWithMoreThan4Managers(employeeMap);
            List<Employee> managersWithMoreSalary = utilService.findManagersWithMoreSalary( hierarchyMap, employeeMap);
            List<Employee> managersWithLessSalary = utilService.findManagersWithLessSalary(hierarchyMap, employeeMap);
            response.setEmployeesWithMoreManagers(employeesWithMoreManagers);
            response.setManagersWithMoreSalary(managersWithMoreSalary);
            response.setManagersWithLessSalary(managersWithLessSalary);
            response.setStatus(CommonConstants.SUCCESS_STATUS);
        } catch (Exception e) {
            response.setError(e.getMessage());
            response.setStatus(CommonConstants.ERROR_STATUS);
        }
        return response;
    }
}

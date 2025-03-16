package org.assignment.controller;

import org.assignment.dto.Employee;
import org.assignment.exception.ReadCSVException;
import org.assignment.model.EmployeeRequest;
import org.assignment.model.EmployeeResponse;
import org.assignment.service.ReaderService;
import org.assignment.service.UtilService;
import org.assignment.util.CommonConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @Mock
    private ReaderService readerService;

    @Mock
    private UtilService utilService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployeeListReturnsCorrectResponse() throws Exception {
        EmployeeRequest request = new EmployeeRequest();
        request.setPathToFile("path/to/file");

        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<String>> hierarchyMap = new HashMap<>();
        List<Employee> employeesWithMoreManagers = Collections.emptyList();
        List<Employee> managersWithMoreSalary = Collections.emptyList();
        List<Employee> managersWithLessSalary = Collections.emptyList();

        Mockito.doNothing().when(readerService).readData(request.getPathToFile(), employeeMap, hierarchyMap);
        when(utilService.findEmployeesWithMoreThan4Managers(employeeMap)).thenReturn(employeesWithMoreManagers);
        when(utilService.findManagersWithMoreSalary(hierarchyMap, employeeMap)).thenReturn(managersWithMoreSalary);
        when(utilService.findManagersWithLessSalary(hierarchyMap, employeeMap)).thenReturn(managersWithLessSalary);

        EmployeeResponse response = employeeController.getEmployeeList(request);

        assertEquals(CommonConstants.SUCCESS_STATUS, response.getStatus());
        assertEquals(employeesWithMoreManagers, response.getEmployeesWithMoreManagers());
        assertEquals(managersWithMoreSalary, response.getManagersWithMoreSalary());
        assertEquals(managersWithLessSalary, response.getManagersWithLessSalary());
    }

    @Test
    void getEmployeeListHandlesException() throws Exception {
        EmployeeRequest request = new EmployeeRequest();
        request.setPathToFile("path/to/file");

        doThrow(new ReadCSVException("Error reading CSV")).when(readerService).readData(request.getPathToFile(), new HashMap<>(), new HashMap<>());

        EmployeeResponse response = employeeController.getEmployeeList(request);

        assertEquals(CommonConstants.ERROR_STATUS, response.getStatus());
        assertEquals("Error reading CSV", response.getError());
    }
}

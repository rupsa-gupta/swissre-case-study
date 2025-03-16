package org.assignment.service.impl;

import org.assignment.dto.Employee;
import org.assignment.exception.ReadCSVException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReaderServiceImplTest {

    @InjectMocks
    private ReaderServiceImpl readerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(readerService, "idHeader", "id");
        ReflectionTestUtils.setField(readerService, "salaryHeader", "salary");
        ReflectionTestUtils.setField(readerService, "managerIdHeader", "managerId");
        ReflectionTestUtils.setField(readerService, "firstNameHeader", "firstName");
        ReflectionTestUtils.setField(readerService, "lastNameHeader", "lastName");
    }

    @Test
    void readDataSuccessfullyReadsCSV() throws Exception {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<String>> hierarchyMap = new HashMap<>();

        readerService.readData("src/test/resources/valid.csv", employeeMap, hierarchyMap);
        assertEquals(5, employeeMap.size());
        assertEquals(3, hierarchyMap.size());

    }

    @Test
    void readDataThrowsExceptionForEmptyCSV() {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<String>> hierarchyMap = new HashMap<>();

        assertThrows(ReadCSVException.class, () -> readerService.readData("src/test/resources/empty.csv", employeeMap, hierarchyMap));
    }

    @Test
    void readDataThrowsExceptionForFileNotFound() {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<String>> hierarchyMap = new HashMap<>();

        assertThrows(ReadCSVException.class, () -> readerService.readData("src/test/resources/nonexistent.csv", employeeMap, hierarchyMap));
    }

    @Test
    void readDataThrowsExceptionForCsvValidationException() {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<String>> hierarchyMap = new HashMap<>();

        assertThrows(ReadCSVException.class, () -> readerService.readData("src/test/resources/invalid.csv", employeeMap, hierarchyMap));
    }
}

package org.assignment.service;

import org.assignment.dto.Employee;
import org.assignment.exception.ReadCSVException;

import java.util.List;
import java.util.Map;

public interface ReaderService {
    void readData(String path, Map<String, Employee> employeeMap, Map<String, List<String>> hierarchyMap) throws ReadCSVException;
}

package org.assignment.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.StringUtils;
import org.assignment.dto.Employee;
import org.assignment.exception.ReadCSVException;
import org.assignment.service.ReaderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assignment.util.CommonConstants.CSV_FILE_IS_EMPTY;
import static org.assignment.util.CommonConstants.ERROR_CSV_VALIDATION;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Value("${csv.header.id}")
    private String idHeader;
    @Value("${csv.header.salary}")
    private String salaryHeader;
    @Value("${csv.header.manager}")
    private String managerIdHeader;
    @Value("${csv.header.firstName}")
    private String firstNameHeader;
    @Value("${csv.header.lastName}")
    private String lastNameHeader;

    @Override
    public void readData(String path, Map<String, Employee> employeeMap, Map<String, List<String>> hierarchyMap) throws ReadCSVException {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(path))
                .withSkipLines(0) // Make sure headers are read first
                .build()) {

            String[] headers = csvReader.readNext(); // Read the headers
            if (headers == null) {
                throw new ReadCSVException(CSV_FILE_IS_EMPTY);
            }

            String[] rowRecord;

            // Read the CSV file line by line
            while ((rowRecord = csvReader.readNext()) != null) {
                // Convert the record into a map with headers as keys
                Map<String, String> rowMap = createRowMap(headers, rowRecord);

                // Extract the values using headers dynamically
                String id = rowMap.get(idHeader);
                String salary = rowMap.get(salaryHeader);
                String managerId = rowMap.get(managerIdHeader);
                String firstName = rowMap.get(firstNameHeader);
                String lastName = rowMap.get(lastNameHeader);

                Employee employee = new Employee(id, firstName, lastName, Double.parseDouble(salary), managerId);
                employeeMap.put(id, employee);

                if(!StringUtils.isEmpty(managerId)){
                    hierarchyMap.computeIfAbsent(managerId, k -> new ArrayList<>()).add(id);
                }


            }
        } catch(CsvValidationException cve){
            throw new ReadCSVException(ERROR_CSV_VALIDATION, cve);
        }
        catch (IOException e) {
            throw new ReadCSVException(e.getMessage(), e);
        }

    }

    private static Map<String, String> createRowMap(String[] headers, String[] csvRec) {
        Map<String, String> rowMap = new java.util.HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            rowMap.put(headers[i], csvRec[i]);
        }
        return rowMap;
    }
}

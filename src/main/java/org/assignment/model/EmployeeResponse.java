package org.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assignment.dto.Employee;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse extends BaseResponse{
    private List<Employee> employeesWithMoreManagers;
    private List<Employee> managersWithMoreSalary;
    private List<Employee> managersWithLessSalary;
}

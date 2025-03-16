package org.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private double salary;
    private String managerId;
}
package org.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequest {
    @NotBlank(message = "absolute file path is required")
    String pathToFile;
}

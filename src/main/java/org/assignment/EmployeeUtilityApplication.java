package org.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmployeeUtilityApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(EmployeeUtilityApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
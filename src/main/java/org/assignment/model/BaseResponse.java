package org.assignment.model;

import lombok.Data;

@Data
public class BaseResponse {
    private String status;
    private String message;
    private String error;
}

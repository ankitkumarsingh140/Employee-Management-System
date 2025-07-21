package com.employee.management.dto;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String name;
    private String email;
    private String department;
    private Double salary;
    private Long userId; // link to existing UserInfo
}


package com.employee.management.dto;

import lombok.Data;

@Data
public class UserWithEmployeeRequest {
    // User part
    private String username;
    private String password;
    private String role;

    // Employee part
    private String name;
    private String email;
    private String department;
    private Double salary;
}

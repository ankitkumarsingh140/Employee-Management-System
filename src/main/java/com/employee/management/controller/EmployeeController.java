package com.employee.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee.management.config.View;
import com.employee.management.dto.EmployeeRequest;
import com.employee.management.dto.UserInfoResponse;
import com.employee.management.dto.UserWithEmployeeRequest;
import com.employee.management.model.Employee;
import com.employee.management.model.UserInfo;
import com.employee.management.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/get")
     @JsonView(View.Public.class)
    public ResponseEntity<Object> getAllEmployees() {
        return service.getAllEmployees();
    }
   
    @GetMapping("/get/{id}")
    @JsonView(View.Internal.class)
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
        return service.saveEmployee(employee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return service.updateEmployee(id, employee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEmployee( @PathVariable Long id) {
        return service.deleteEmployee(id);
    }

    @PostMapping("/register-employee-user")
      public ResponseEntity<String> registerUserAndEmployee(@RequestBody UserWithEmployeeRequest request) {
            return service.registerUserAndEmployee(request);
       }

    @PostMapping("/register/user")
    public ResponseEntity<UserInfoResponse> registerUser(@RequestBody UserInfo user) {
         UserInfo savedUser = service.registerUser(user).getBody();
         UserInfoResponse response  = new UserInfoResponse(
           savedUser.getId(),
          savedUser.getUsername(),
          savedUser.getRoles()
         );
        return ResponseEntity.ok(response);
      }
    
      @PostMapping("/register/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest request) {
    return service.createEmployee(request);
}

}
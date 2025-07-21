package com.employee.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.employee.management.dto.EmployeeRequest;
import com.employee.management.dto.UserWithEmployeeRequest;
import com.employee.management.model.Employee;
import com.employee.management.model.UserInfo;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.repository.UserInfoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private UserInfoRepository userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    


    public ResponseEntity<Object> getAllEmployees() {
    List<Employee> employees = repository.findAll();

    if (employees.isEmpty()) {
    return ResponseEntity.status(404).body("No employees found");
}
    return ResponseEntity.ok(employees);
}

    public ResponseEntity<Object> getEmployeeById(Long id) {
       Optional<Employee> emp =  repository.findById(id);
       if(!emp.isPresent()){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Data Found for id");
       }
        return ResponseEntity.ok(emp.get());
    }

    public ResponseEntity<Object> saveEmployee(Employee employee) {
         try {
            Employee emp = repository.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(emp);
         } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in saving");
         }
      
    }

    public ResponseEntity<Object> updateEmployee(Long id, Employee updated) {
       
        Optional<Employee> emp = repository.findById(id);
        if(!emp.isPresent()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Employee savUpdatedEmployee = emp.get();
        savUpdatedEmployee.setName(updated.getName());
        savUpdatedEmployee.setEmail(updated.getEmail());
        savUpdatedEmployee.setDepartment(updated.getDepartment());
        savUpdatedEmployee.setSalary(updated.getSalary());
       repository.save(savUpdatedEmployee);
        return  ResponseEntity.ok(savUpdatedEmployee);
    }

    public ResponseEntity<Object> deleteEmployee(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(match->match.getAuthority().equals("ADMIN"));
        if(isAdmin){
        Optional <Employee> optionalEmployee = repository.findById(id);
        if(!optionalEmployee.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not Authorized please connect with Admin");
    }

    public ResponseEntity<String> registerUserAndEmployee(UserWithEmployeeRequest request) {
         System.out.println("Checking username: " + request.getUsername());
    if (userRepo.findByUsername(request.getUsername()).isPresent()) {
        return ResponseEntity.badRequest().body("Username already exists.");
    }


    // Create user
    UserInfo user = new UserInfo();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRoles(request.getRole());
    userRepo.save(user);

    // Create employee and link
    Employee emp = new Employee();
    emp.setName(request.getName());
    emp.setEmail(request.getEmail());
    emp.setDepartment(request.getDepartment());
    emp.setSalary(request.getSalary());
    emp.setUserInfo(user); // Foreign key link

    repository.save(emp);

    return ResponseEntity.ok("User and employee created successfully.");
}

public ResponseEntity<UserInfo> registerUser(UserInfo user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    UserInfo saved = userRepo.save(user);
    return ResponseEntity.ok(saved);
}

public ResponseEntity<Employee> createEmployee(EmployeeRequest request) {
    UserInfo user = userRepo.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Employee emp = new Employee();
    emp.setName(request.getName());
    emp.setEmail(request.getEmail());
    emp.setDepartment(request.getDepartment());
    emp.setSalary(request.getSalary());
    emp.setUserInfo(user); // FK link

    return ResponseEntity.ok(repository.save(emp));
}
}
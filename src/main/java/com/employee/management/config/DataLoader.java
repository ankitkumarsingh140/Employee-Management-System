package com.employee.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.employee.management.model.Employee;
import com.employee.management.model.UserInfo;
import com.employee.management.repository.EmployeeRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        UserInfo user = new UserInfo();
        user.setUsername("johndoe");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRoles("EMPLOYEE");

        Employee emp = new Employee();
        emp.setName("John Doe");
        emp.setEmail("john.doe@example.com");
        emp.setDepartment("IT");
        emp.setSalary(55000.0);
        emp.setUserInfo(user); // 🔗 link UserInfo
 employeeRepository.save(emp); // saves both Employee and UserInfo

        UserInfo user2 = new UserInfo();
        user2.setUsername("Ankit");
        user2.setPassword(passwordEncoder.encode("password123"));
        user2.setRoles("ADMIN");

        Employee emp2 = new Employee();
        emp2.setName("Ankit K Singh");
        emp2.setEmail("Ankit.ks@example.com");
        emp2.setDepartment("ADMIN");
        emp2.setSalary(55000.0);
        emp2.setUserInfo(user2); // 🔗 link UserInfo
     employeeRepository.save(emp2); // saves both Employee and UserInfo

         UserInfo user3 = new UserInfo();
        user3.setUsername("vicky");
        user3.setPassword(passwordEncoder.encode("password123"));
        user3.setRoles("OTHER");

        Employee emp3 = new Employee();
        emp3.setName("V Singh");
        emp3.setEmail("v.ks@example.com");
        emp3.setDepartment("OTHER");
        emp3.setSalary(55000.0);
        emp3.setUserInfo(user3); // 🔗 link UserInfo

        employeeRepository.save(emp3); // saves both Employee and UserInfo
    }
}


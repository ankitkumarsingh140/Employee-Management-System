package com.employee.management.model;


import com.employee.management.config.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Public.class)
    private Long id;
    @JsonView(View.Public.class)
    private String name;
    @JsonView(View.Public.class)
    private String email;
    @JsonView(View.Public.class)
    private String department;
    @JsonView(View.Public.class)
    private Double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonView(View.Internal.class)
    private UserInfo userInfo;
}

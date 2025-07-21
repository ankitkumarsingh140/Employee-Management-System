package com.employee.management.model;

import com.employee.management.config.View;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UserInfo {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Internal.class)
    private Long id;

   @JsonView(View.Internal.class)
    private String username;
    @JsonView(View.Internal.class)
    private String password;
    @JsonView(View.Internal.class)
    private String roles;

    @OneToOne(mappedBy = "userInfo")
    private Employee employee; // Example: "ROLE_ADMIN" or "ROLE_USER"

     public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    @Override
    public String toString() {
        return "UserInfo [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles + "]";
    }



}

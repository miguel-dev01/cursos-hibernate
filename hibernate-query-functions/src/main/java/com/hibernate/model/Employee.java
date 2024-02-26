package com.hibernate.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String address;
    private Double salary;

    @ElementCollection
    private List<String> phones;
    
    private LocalDateTime joinDate;

    public Employee() {
    }

    public Employee(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Employee(String name, String email, String address, Double salary, LocalDateTime joinDate) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Double getSalary() {
        return salary;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", salary="
                + salary + ", phones=" + phones + ", joinDate=" + joinDate + "]";
    }

}

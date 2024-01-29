package com.hibernate.model.onetomany;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cif;

    @OneToMany(mappedBy = "company")
    @JoinTable(name = "company_employee", 
             joinColumns = @JoinColumn(name = "company_id"), 
             inverseJoinColumns = @JoinColumn(name = "employee_id"))
    Set<Employee> employees = new HashSet<Employee>();

    public Company() {
    }

    public Company(String cif) {
        this.cif = cif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Company [id=" + id + ", cif=" + cif + "]";
    }

}

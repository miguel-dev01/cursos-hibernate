package com.hibernate.model.manytomany;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", projects=" + projects + "]";
    }

}

package com.hibernate.model;

import jakarta.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    // private Long id;

    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + "]";
    }

}

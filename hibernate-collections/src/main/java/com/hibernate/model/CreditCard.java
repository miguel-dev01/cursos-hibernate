package com.hibernate.model;

import jakarta.persistence.*;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number, name;

    public CreditCard() {
    }

    public CreditCard(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreditCard [id=" + id + ", name=" + name + ", number=" + number + "]";
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

}

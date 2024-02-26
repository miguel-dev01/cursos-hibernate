package com.hibernate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // Por defecto EAGER
    // es decir, se cargará la dirección cada vez que se cargue un autor
    // pero el inconveniente es que si se cargan muchos autores, se cargarán muchas direcciones
    // y esas direcciones cargaran a su vez mas datos y asi... Lo que puede ser probkema de 
    // rendimiento
    // Se debe usar LAZY para que la dirección se cargue solo cuando se necesite
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    // Por defecto LAZY
    // es decir, se cargarán los libros solo cuando se acceda a ellos
    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();

    public Author() {}

    public Author(String name, String email, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Author [address=" + address + ", birthDate=" + birthDate + ", email=" + email + ", id=" + id + ", name="
                + name + "]";
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

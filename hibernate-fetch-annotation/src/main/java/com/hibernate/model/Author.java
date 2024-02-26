package com.hibernate.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Por defecto LAZY -> Cuando acceda a los libros
    // es cuando se van a cargar los datos
    // @Fetch(value = FetchMode.SELECT)
    // Se hace una consulta padre, y si se quiere acceder a mas datos o tablas relacionadas,
    // se realiza una consulta por cada uno de los registros hijos
    // ----------------------------------------------------------------
    // @Fetch(value = FetchMode.SUBSELECT)
    // Se realizara una consulta padre y una consulta hija, donde en la hija se ejecuta 
    // un SELECT con un IN para traer los datos de los hijos, o tambien se le puede decir
    // subconsulta.
    @Fetch(value = FetchMode.JOIN)
    // Se hara una consulta para traer los libros y los capitulos
    // donde se realizan estas asociaciones en esta unica consulta
    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + "]";
    }

}

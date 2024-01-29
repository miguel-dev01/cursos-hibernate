package com.hibernate.model.maps;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "authors_book", 
            joinColumns = @JoinColumn(name = "author_id"), 
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @MapKey(name = "isbn")
    private Map<String, Book> books = new HashMap<String, Book>();

    public Author() {
    }

    public Author(String name) {
        this.name = name;
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

    public Map<String, Book> getBooks() {
        return books;
    }

    public void setBooks(Map<String, Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + ", books=" + books + "]";
    }

}

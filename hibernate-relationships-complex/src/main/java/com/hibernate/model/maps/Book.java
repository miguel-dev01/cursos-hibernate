package com.hibernate.model.maps;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String isbn;

    private Double price;

    public Book() {
    }

    public Book(String title, String isbn, Double price) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", isbn=" + isbn + ", price=" + price + ", title=" + title + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}

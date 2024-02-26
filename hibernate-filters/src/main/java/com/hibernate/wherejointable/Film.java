package com.hibernate.wherejointable;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.WhereJoinTable;

import jakarta.persistence.*;

@SuppressWarnings("deprecation")
@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    @ManyToMany
    @WhereJoinTable(clause = "classification='APTO'")
    @JoinTable(name = "film_genre", 
            joinColumns = @JoinColumn(name = "film_id"), 
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    public Film() {
    }

    public Film(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Film [id=" + id + ", title=" + title + ", genres=" + genres + "]";
    }

}

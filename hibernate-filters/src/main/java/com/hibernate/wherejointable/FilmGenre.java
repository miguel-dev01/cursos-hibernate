package com.hibernate.wherejointable;

import jakarta.persistence.*;

@Entity(name = "film_genre")
public class FilmGenre {

    @Id
    @Column(name = "film_id", insertable = false, updatable = false)
    private Long idFilm;

    @Id
    @Column(name = "genre_id", insertable = false, updatable = false)
    private Long idGenre;

    private Integer minAge;

    private String classification;

    public FilmGenre() {
    }

    public FilmGenre(Long idFilm, Long idGenre, Integer minAge, String classification) {
        this.idFilm = idFilm;
        this.idGenre = idGenre;
        this.minAge = minAge;
        this.classification = classification;
    }

    public Long getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Long idFilm) {
        this.idFilm = idFilm;
    }

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return "FilmGenre [idFilm=" + idFilm + ", idGenre=" + idGenre + ", minAge=" + minAge + ", classification="
                + classification + "]";
    }

}

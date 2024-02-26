package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.wherejointable.*;

public class WhereJoinTableTest {

    @Test
    void test() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();
        session.createQuery("from Film", Film.class)
                .getResultList()
                .forEach(film -> film.getGenres().forEach(System.out::println));
    }

    void insertData() {
    
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        var film1 = new Film("The Tempest");
        var film2 = new Film("Great Expectations");
        var film3 = new Film("Avengers: Endgame");
        var film4 = new Film("Matrix");
        session.persist(film1);
        session.persist(film2);
        session.persist(film3);
        session.persist(film4);

        var genre1 = new Genre("comedies");
        var genre2 = new Genre("fiction");
        var genre3 = new Genre("action");
        session.persist(genre1);
        session.persist(genre2);
        session.persist(genre3);

        var fm1 = new FilmGenre(film1.getId(), genre1.getId(), 12, "APTO");
        var fm2 = new FilmGenre(film1.getId(), genre2.getId(), 12, "APTO");
        var fm3 = new FilmGenre(film2.getId(), genre3.getId(), 12, "APTO");
        var fm4 = new FilmGenre(film4.getId(), genre1.getId(), 12, "NO APTO");
        session.persist(fm1);
        session.persist(fm2);
        session.persist(fm3);
        session.persist(fm4);

        session.getTransaction().commit();
    }


}

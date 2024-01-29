package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.maps.*;

public class MapTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var author1 = session.find(Author.class, 1L);
        author1.getBooks().forEach((clave, valor) -> System.out.println(clave + " " + valor));
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var author1 = new Author("John");
        var author2 = new Author("Mary");
        session.persist(author1);
        session.persist(author2);

        var book1 = new Book("Java 11", "12345678", 100.0);
        var book2 = new Book("Java 12", "87654321", 200.0);
        var book3 = new Book("Java 13", "11111111", 300.0);
        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        author1.getBooks().put(book1.getIsbn(), book1);
        author1.getBooks().put(book2.getIsbn(), book2);
        author1.getBooks().put(book3.getIsbn(), book3);

        session.persist(author1);

        session.getTransaction().commit();
    }

}

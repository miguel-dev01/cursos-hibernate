package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

public class FetchingManyTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                SELECT a FROM Author a 
                JOIN FETCH a.books
                WHERE a.id = :id
                """;

        var author1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println("===================================");
        author1.getBooks().forEach(System.out::println);
        session.close();
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        var author1 = new Author("author1", "a1@email.com", null);
        var author2 = new Author("author2", "a2@email.com", null);
        var author3 = new Author("author3", "a3@email.com", null);
        var book1 = new Book("book1", 1.0, 100, true, author1);
        var book2 = new Book("book2", 2.0, 200, true, author1);
        var book3 = new Book("book3", 3.0, 300, true, author1);

        session.beginTransaction();

        session.persist(author1);
        session.persist(author2);
        session.persist(author3);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();
    }

}

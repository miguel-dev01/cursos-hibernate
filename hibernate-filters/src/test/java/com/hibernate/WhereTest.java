package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.where.*;

public class WhereTest {

    @Test
    void test() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();
        var author = session.find(Author.class, 1L);
        author.getBooks().forEach(System.out::println);

    }

    void insertData() {
    
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        var author1 = new Author("William Shakespeare");
        var author2 = new Author("Charles Dickens");

        session.persist(author1);
        session.persist(author2);

        var book1 = new Book("The Tempest", "comedies", author1);
        var book2 = new Book("Great Expectations", "comedies", author2);
        var book3 = new Book("Book3", "fiction", author1);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();
    }

}

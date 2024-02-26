package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

public class CascadeTest {

    @Test
    void oneToOne() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var author = session.find(Author.class, 1L);
        System.out.println(author);
    }

    @Test
    void oneToMany() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var author = session.find(Author.class, 1L);
        var book1 = new Book("Book 1", "1234", author);
        var book2 = new Book("Book 2", "5678", author);

        session.beginTransaction();
        session.persist(book1);
        session.persist(book2);
        session.getTransaction().commit();
        
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        author = session.find(Author.class, 1L);
        author.setAge(33);
        session.beginTransaction();
        session.merge(author);
        session.getTransaction().commit();
        
        System.out.println(author);
    }

    @Test
    void manyToOne() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var author = new Author("Author 3", 50, null);

        var book1 = new Book("Book 3", "91011", author);
        var book2 = new Book("Book 4", "121314", author);
        var book3 = new Book("Book 5", "151617", author);

        // Al tener el cascade en la relacion ManyToOne, no es necesario persistir el autor
        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();

        session.close();
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var address1 = new Address("Calle 123", "Madrid");
        var address2 = new Address("Calle 456", "Barcelona");

        session.persist(address1);
        session.persist(address2);

        var author1 = new Author("Author 1", 30, address1);
        var author2 = new Author("Author 2", 40, address2);

        session.persist(author1);
        session.persist(author2);

        session.getTransaction().commit();
    }

    @Test
    void oneToManyRemoveAsociation() {
        var session = HibernateUtil.getSessionFactory().openSession();

        var author = new Author("Author 3", 50, null);
        var book1 = new Book("Book 1", "1234", author);
        var book2 = new Book("Book 2", "5678", author);

        session.beginTransaction();
        session.persist(book1);
        session.persist(book2);
        session.getTransaction().commit();
        
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        author = session.find(Author.class, 1L);

        session.beginTransaction();
        author.getBooks().remove(book1);
        session.getTransaction().commit();
        
        System.out.println(author);
    }

}

package com.hibernate;

import org.hibernate.graph.GraphParser;
import org.hibernate.graph.RootGraph;
import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

import jakarta.persistence.EntityGraph;

public class EntityGraphTest {

    @Test
    void graph1() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        EntityGraph<Author> graph = session.createEntityGraph(Author.class);

        session.createQuery("SELECT a FROM Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> System.out.println(author.getBooks().size()));
    }

    @Test
    void entityGraph1Level() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        RootGraph<?> graph = session.createEntityGraph("authorWithBooks");

        session.createQuery("SELECT a FROM Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> System.out.println(author.getBooks().size()));
    }

    @Test
    void graph2Level() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var graph = session.createEntityGraph("authorWithBooksAndChapters");

        session.createQuery("SELECT a FROM Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> author.getBooks().forEach(
                    book -> System.out.println(book.getChapters().size())));
    }

    @Test
    void entityGraph2LevelGraphExpression() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var graph = GraphParser.parse(Author.class, 
                "books(chapters)", session);

        session.createQuery("SELECT a FROM Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> System.out.println(author.getBooks().size()));
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var author = new Author("Author 1");
        var author2 = new Author("Author 2");
        session.persist(author);
        session.persist(author2);

        var book1 = new Book("Book 1", author);
        var book2 = new Book("Book 2", author);
        var book3 = new Book("Book 3", author2);
        var book4 = new Book("Book 4", author2);
        session.persist(book1);
        session.persist(book2);
        session.persist(book3);
        session.persist(book4);

        var chapter1 = new Chapter("Chapter 1", book1);
        var chapter2 = new Chapter("Chapter 2", book1);
        var chapter3 = new Chapter("Chapter 3", book2);
        var chapter4 = new Chapter("Chapter 4", book2);
        var chapter5 = new Chapter("Chapter 5", book3);
        var chapter6 = new Chapter("Chapter 6", book3);
        var chapter7 = new Chapter("Chapter 7", book4);
        var chapter8 = new Chapter("Chapter 8", book4);

        session.persist(chapter1);
        session.persist(chapter2);
        session.persist(chapter3);
        session.persist(chapter4);
        session.persist(chapter5);
        session.persist(chapter6);
        session.persist(chapter7);
        session.persist(chapter8);

        session.getTransaction().commit();
    }

}

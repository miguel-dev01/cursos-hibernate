package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

public class FetchTest {

    @Test
    void withoutFetch() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var a1 = session.find(Author.class, 1L);
        System.out.println("===================================");
        System.out.println(a1.getBooks());
        System.out.println("===================================");
        // Del autor obtenemos los libros y de los libros los capitulos
        a1.getBooks().forEach(b -> System.out.println(b.getChapters()));
    }

    // Metodo para revisar el FetchMode.SELECT normal
    @Test
    void fetchSelect() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var authors = session.createQuery("SELECT a FROM Author a", 
                Author.class).getResultList();
        System.out.println("===================================");
        authors.forEach(a -> System.out.println(a.getBooks()));
    }

    // Tener en cuenta que cuando el campo esté en @Fetch(value = FetchMode.JOIN)
    // si, por ejemplo solo queremos obtener el nombre del autor, 
    // se van a traer todos los datos de los libros y capitulos, lo cual no seria
    // necesario ni optimo. Por tanto lo optimo para ese caso crear una consulta que haga el JOIN
    // y solo traiga los datos que necesitamos.
    @Test
    void fetchJoin() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select a from Author a
                join fetch a.books b 
                join fetch b.chapters c
                where a.id = :id
                """;
        var author = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println("===================================");
        System.out.println(author.getBooks());
        author.getBooks().forEach(b -> System.out.println(b.getChapters()));
    }

    // Metodo para revisar el FetchMode.SUBSELECT
    @Test
    void fetchSubselect() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var authors = session.createQuery("SELECT a FROM Author a", 
                Author.class).getResultList();
        System.out.println("===================================");
        authors.forEach(a -> System.out.println(a.getBooks()));

    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        var author = new Author("Author 1");
        var author2 = new Author("Author 2");

        var book = new Book("Book 1", 100.0, author);
        var book2 = new Book("Book 2", 200.0, author2);
        var book3 = new Book("Book 3", 300.0, author2);

        var chapter = new Chapter("Chapter 1", book);
        var chapter2 = new Chapter("Chapter 2", book);
        var chapter3 = new Chapter("Chapter 3", book2);
        var chapter4 = new Chapter("Chapter 4", book2);
        var chapter5 = new Chapter("Chapter 5", book3);
        var chapter6 = new Chapter("Chapter 6", book3);

        
        session.beginTransaction();

        session.persist(author);
        session.persist(author2);

        session.persist(book);
        session.persist(book2);
        session.persist(book3);

        session.persist(chapter);
        session.persist(chapter2);
        session.persist(chapter3);
        session.persist(chapter4);
        session.persist(chapter5);
        session.persist(chapter6);

        session.getTransaction().commit();
    }

}

package com.hibernate;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.Test;

import com.hibernate.model.Author;
import com.hibernate.model.Book;

public class CriteriaTest {

    @Test
    void findAll() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Author> criteria = builder.createQuery(Author.class);

        JpaRoot<Author> root = criteria.from(Author.class);
        criteria.select(root);

        var authors = session.createQuery(criteria).list();
        authors.forEach(System.out::println);
    }

    @Test
    void findById() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        var root = criteria.from(Author.class);

        criteria.select(root)
                .where(builder.equal(root.get("id"), 1L));

        var author = session.createQuery(criteria).getSingleResult();
        System.out.println(author);
    }

    @Test
    void findByEmailLike() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        var root = criteria.from(Author.class);

        criteria.select(root)
                .where(builder.like(root.get("email"), "%java.es%"));

        /**
         * Autores filtrados por email que contenga java.es
         */
        var authorsFromDB = session.createQuery(criteria).getResultList();
        System.out.println(authorsFromDB);
    }

    @Test
    void findPriceGreaterThan() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        var root = criteria.from(Book.class);

        criteria.select(root)
                .where(builder.greaterThanOrEqualTo(root.get("price"), 69.99));

        var result = session.createQuery(criteria).getResultList();
        result.forEach(System.out::println);
    }

    @Test
    void findBetween() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        JpaRoot<Author> root = criteria.from(Author.class);

        criteria.select(root)
                .where(builder.between(root.get("birthDate"),
                        LocalDate.of(1993, 1, 1),
                        LocalDate.of(2001, 1, 1)));

        var authors = session.createQuery(criteria).getResultList();
        authors.forEach(System.out::println);
    }

    @Test
    void findMultipleWhere() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        JpaRoot<Book> root = criteria.from(Book.class);

        var priceMedium = builder.greaterThanOrEqualTo(root.get("price"), 69.99);
        var numPagesFilter = builder.lessThanOrEqualTo(root.get("numPages"), 300);

        criteria.select(root).where(builder.and(priceMedium, numPagesFilter));

        var resultBooks = session.createQuery(criteria).getResultList();
        resultBooks.forEach(System.out::println);
    }

    @Test
    void multiSelect() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var builder = session.getCriteriaBuilder();
        var criteria = builder.createQuery(Object[].class);
        var root = criteria.from(Book.class);

        criteria.multiselect(root.get("title"), root.get("price"));

        List<Object[]> resultBooks = session.createQuery(criteria).getResultList();
        for (Object[] book : resultBooks) {
            System.out.println("Title: " + book[0] + ", Price: " + book[1]);
        }
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var author1 = new Author("author1",
            "author1@java.es", LocalDate.of(1990, 10, 25));
        var author2 = new Author("author2",
                "author2@email.es", LocalDate.of(1995, 10, 25));
        var author3 = new Author("author3",
                "author3@java.es", LocalDate.of(2000, 10, 25));

        session.persist(author1);
        session.persist(author2);
        session.persist(author3);

        var book1 = new Book("book1", 49.99, 150, false, author1);
        var book2 = new Book("book2", 99.99, 250, true, author1);
        var book3 = new Book("book3", 149.99, 350, false, author1);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();
    }
}

package com.hibernate;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

public class FetchingAllTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                SELECT a FROM Author a 
                JOIN FETCH a.address ad
                JOIN FETCH a.books b
                JOIN FETCH b.categories c
                WHERE a.id = :id
                """;

        var author1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println("===================================");
        System.out.println(author1.getAddress());
        // Obtiene las categorias de los libros para ese autor
        author1.getBooks().forEach(b -> {
            System.out.println(b.getCategories());
        });
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        var address = new Address("Calle 123", "Buenos Aires", "Argentina");
        var address2 = new Address("Calle 456", "Buenos Aires", "Argentina");
        var address3 = new Address("Calle 789", "Buenos Aires", "Argentina");
        
        var author = new Author("Miguel de Cervantes", "miguel@cervantes.es", null);
        author.setAddress(address);
        var author2 = new Author("William Shakespeare", "william@email.com", null);
        var author3 = new Author("Homer", "homero@email.com", null);

        var category = new Category("Category 1", 5);
        var category2 = new Category("Category 2", 10);
        var book1 = new Book("book1", 1.0, 100, true, author);
        book1.setCategories(new HashSet<>(Set.of(category, category2)));
        var book2 = new Book("book2", 2.0, 200, true, author);
        book2.setCategories(new HashSet<>(Set.of(category, category2)));
        var book3 = new Book("book3", 3.0, 300, true, author);
        book3.setCategories(new HashSet<>(Set.of(category)));

        session.beginTransaction();

        session.persist(address);
        session.persist(address2);
        session.persist(address3);
        session.persist(author);
        session.persist(author2);
        session.persist(author3);
        session.persist(category);
        session.persist(category2);
        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();
    }
}

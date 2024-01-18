package com.hibernate;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.Address;
import com.hibernate.model.Author;
import com.hibernate.model.Book;

public class OneToManyTest {

    @Test
    void oneToMany() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var author1 = session.find(Author.class, 1L);
        System.out.println(author1.getBooks());
        
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var author = new Author("Arturo Perez-Reverte",
                "arturo@perez.es", LocalDate.of(1951, 11, 25));
        var author2 = new Author("Javier Sierra",
                "javier@sierra.es", LocalDate.of(1971, 11, 25));

        var address1 = new Address("Calle de la piruleta", "Madrid", "España");
        var address2 = new Address("Calle del oso", "Murcia", "España");

        author.setAddress(address1);
        author2.setAddress(address2);

        session.persist(address1);
        session.persist(address2);
        session.persist(author);
        session.persist(author2);

        var book1 = new Book("El club Dumas", 19.99, 500, true, author);
        var book2 = new Book("El peregrino", 49.99, 300, true, author);
        var book3 = new Book("Los Iluminados", 99.99, 250, false, author2);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();
    }

}

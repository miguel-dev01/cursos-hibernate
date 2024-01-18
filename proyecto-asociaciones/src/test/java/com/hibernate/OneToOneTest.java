package com.hibernate;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import com.hibernate.model.Author;
import com.hibernate.model.Address;

public class OneToOneTest {

    @Test
    void oneToOne() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var author1 = session.find(Author.class, 1L);
        System.out.println(author1.getAddress());

        var author2 = session.find(Author.class, 2L);
        System.out.println(author2.getAddress());
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

        session.getTransaction().commit();
    }

}

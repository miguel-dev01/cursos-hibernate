package com.hibernate;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hibernate.model.Address;
import com.hibernate.model.Author;

public class QueriesTest {

    @Test
    void findAll() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a";

        var query = session.createQuery(jpql, Author.class);
        query.list().forEach(System.out::println);

    }

    @Test
    void findByEmail() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.email = :email";

        var query = session.createQuery(jpql, Author.class);
        query.setParameter("email", "miguel@cervantes.es");
        var author = query.getSingleResult();
        System.out.println(author);
    }

    @Test
    void findByDates() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.birthDate BETWEEN :start AND :end";
        var query = session.createQuery(jpql, Author.class);

        query.setParameter("start", LocalDate.of(1400, 9, 29));
        query.setParameter("end", LocalDate.of(1900, 9, 29));

        query.list().forEach(System.out::println);
    }

    @Test
    void findByIdIn() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.id IN (:ids)";
        var query = session.createQuery(jpql, Author.class);

        query.setParameter("ids", List.of(1L, 3L));

        query.list().forEach(System.out::println);
    }

    @Test
    void findByAddressCity() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.address.city = :city";
        var query = session.createQuery(jpql, Author.class);

        query.setParameter("city", "Murcia");

        query.list().forEach(System.out::println);
    }

    @Test
    void count() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT count(a.id) FROM Author a";
        var query = session.createQuery(jpql, Long.class);

        Long count = query.getSingleResult();
        System.out.println("Numero de autores: " + count);
    }

    @Test
    void update() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "UPDATE Author a SET a.name = :name WHERE a.id = :id";

        session.beginTransaction();
        int updated = session.createMutationQuery(jpql)
                .setParameter("name", "Ruben Dario")
                .setParameter("id", 3L)
                .executeUpdate();
        session.getTransaction().commit();
        System.out.println("Actualizados " + updated + " autores");
    }

    @Test
    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("Calle Falsa", "Springfield", "EEUU");
        var address2 = new Address("Calle Falsisima", "Murcia", "España");

        session.persist(address1);
        session.persist(address2);

        var author1 = new Author("Miguel de Cervantes",
            "miguel@cervantes.es", LocalDate.of(1547, 9, 29));
        var author2 = new Author("William Shakespeare",
            "william@shakespeare.es", LocalDate.of(1564, 4, 26));
        var author3 = new Author("Autor 3", "autor3@gmail.com", LocalDate.of(1990, 1, 1));
        
        author1.setAddress(address1);
        author2.setAddress(address2);
        //author3.setAddress(address2);

        session.persist(author1);
        session.persist(author2);
        session.persist(author3);

        session.getTransaction().commit();
    }

}

package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

public class FetchingOneTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        var author = session.find(Author.class, 1L);
        System.out.println("===================================");
        
        // Si es lazy, se carga la dirección solo cuando se accede a ella
        System.out.println(author.getAddress().getStreet());
        
        session.close();
    }

    @Test
    void findAuthorByIdWithAddress() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        String jpql = """
                SELECT a FROM Author a 
                JOIN FETCH a.address 
                WHERE a.id = :id
                """;

        var author1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println("===================================");
        System.out.println(author1.getAddress().getStreet());
        session.close();
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

        session.beginTransaction();

        session.persist(address);
        session.persist(address2);
        session.persist(address3);
        session.persist(author);
        session.persist(author2);
        session.persist(author3);

        session.getTransaction().commit();

    }

}

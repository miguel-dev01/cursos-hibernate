package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.Author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class JpaTest {

    @Test
    void jpql() {
        insertData();
        var entityManager = JpaUtil.getEntityManager();

        entityManager.createQuery("SELECT a FROM Author a", Author.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void criteria() {
        insertData();
        var entityManager = JpaUtil.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);

        var root = query.from(Author.class);
        query.select(root);

        entityManager.createQuery(query).getResultList().forEach(System.out::println);
    }

    void insertData() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        
        entityManager.getTransaction().begin();

        var author1 = new Author("Alejandro Dumas");
        var author2 = new Author("Miguel de Cervantes");
        var author3 = new Author("Ruben Dario");

        entityManager.persist(author1);
        entityManager.persist(author2);
        entityManager.persist(author3);

        entityManager.getTransaction().commit();
        entityManager.close();

    }

}

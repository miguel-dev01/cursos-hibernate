package com.hibernate.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hibernate.HibernateUtil;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class CriteriaTest {

    // Hacemos la prueba de proyección de una columna
    @Test
    void projectionOneColumn() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(String.class);
        var root = query.from(Employee.class);

        query.select(root.get("email"));
        session.createQuery(query).getResultList().forEach(System.out::println);
    }

    // Hacemos la prueba de proyección de varias columnas
    @Test
    void projectionMultiColumn() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Object[].class);
        var root = query.from(Employee.class);

        query.multiselect(root.get("email"), 
                root.get("salary"),
                root.get("bonus")
        );
        session.createQuery(query).getResultList().forEach(row -> {
            System.out.println("Email: " + row[0]);
            System.out.println("Salary: " + row[1]);
            System.out.println("Bonus: " + row[2]);
        });
    }

    @Test
    void aggregation() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Object[].class);
        var root = query.from(Employee.class);

        query.multiselect(
                root.get("city"), 
                criteriaBuilder.avg(root.get("salary")),
                criteriaBuilder.avg(root.get("bonus"))
        );
        query.groupBy(root.get("city"))
            .having(criteriaBuilder.gt(criteriaBuilder.avg(root.get("salary")), 1500.0));

        session.createQuery(query).getResultList().forEach(row -> {
            System.out.println("Email: " + row[0]);
            System.out.println("Salary: " + row[1]);
            System.out.println("Bonus: " + row[2]);
        });
    }

    @Test
    void orderBy() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Object[].class);
        var root = query.from(Employee.class);

        var avgSalary = criteriaBuilder.avg(root.get("salary"));
        query.multiselect(
                root.get("city"), 
                avgSalary,
                criteriaBuilder.avg(root.get("bonus"))
        );
        query.groupBy(root.get("city"));
        query.orderBy(criteriaBuilder.desc(avgSalary));

        session.createQuery(query).getResultList().forEach(row -> {
            System.out.println("Email: " + row[0]);
            System.out.println("Salary: " + row[1]);
            System.out.println("Bonus: " + row[2]);
        });
    }

    @Test
    void projectionMultiColumnTuple() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        var root = query.from(Employee.class);

        query.select(
            criteriaBuilder.tuple(
                        root.get("email"), 
                        root.get("salary"),
                        root.get("bonus")
                )
        );
        session.createQuery(query).getResultList().forEach(row -> {
            System.out.println("Email: " + row.get(0));
            System.out.println("Salary: " + row.get(1));
            System.out.println("Bonus: " + row.get(2));
        });
    }

    @Test
    void join() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Author.class);
        var root = query.from(Author.class);

        var books = root.join("books");
        query.select(root)
                .where(
                    criteriaBuilder.equal(books.get("category"), "Ciencia ficcion")
                );
        session.createQuery(query).getResultList().forEach(System.out::println);
    }

    @Test
    void joinFetch() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Author.class);
        var root = query.from(Author.class);

        var books = root.join("books");
        // var booksFetch = root.fetch("books", JoinType.LEFT);
        query.select(root)
                .where(
                    criteriaBuilder.equal(books.get("category"), "Ciencia ficcion")
                );
        session.createQuery(query).getResultList().forEach(System.out::println);
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var phones1 = new ArrayList<String>(List.of("123-456-7890", "123-456-7891"));
        var employee1 = new Employee("", 1000.0, 100.0, 
                "New York", "John Doe", phones1, null);
        
        var phones2 = new ArrayList<String>(List.of("123-456-7892", "123-456-7893"));
        var employee2 = new Employee("", 2000.0, 200.0, 
                "New York", "Jane Doe", phones2, null);

        session.persist(employee1);
        session.persist(employee2);

        var author1 = new Author("Author 1");
        var author2 = new Author("Author 2");

        var book1 = new Book("Book 1", author1, "Ciencia ficcion");
        var book2 = new Book("Book 2", author1, "Novela");
        var book3 = new Book("Book 3", author2, "Ciencia ficcion");
        var book4 = new Book("Book 4", author2, "Novela");

        session.persist(author1);
        session.persist(author2);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);
        session.persist(book4);

        session.getTransaction().commit();
    }

}

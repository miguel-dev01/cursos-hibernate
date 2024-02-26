package com.hibernate.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hibernate.HibernateUtil;

import jakarta.persistence.Tuple;

public class FunctionsTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select coalesce(e.name, e.email) from Employee e
        """;

        session.createQuery(jpql, String.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void size() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e.id as id, size(e.phones) as num_phones
                from Employee e
        """;

        session.createQuery(jpql, Tuple.class)
                .getResultList()
                .forEach(t -> System.out.println(t.get("id") + " - " + t.get("num_phones")));
    }

    @Test
    void concat() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // Es posible introducir coalesce dentro de la funcion concat
        String jpql = """
                select concat(e.name, ' - ', e.address) from Employee e
        """;

        session.createQuery(jpql, String.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void upperLower() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e.id as id, 
                concat(e.email, ' ', upper(coalesce(e.address, 'Main Street'))) as email_address
                from Employee e
        """;

        session.createQuery(jpql, Tuple.class)
                .getResultList()
                .forEach(t -> System.out.println(t.get("id") + " - " + t.get("email_address")));
    }

    @Test
    void replace() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // Funcion de hibernate
        String jpql = """
                select e.id as id, 
                replace(e.email, '@email.com', '') as nickname
                from Employee e
        """;

        session.createQuery(jpql, Tuple.class)
                .getResultList()
                .forEach(t -> System.out.println(t.get("id") + " - " + t.get("nickname")));
    }

    @Test
    void extract() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        // String jpql = """
        //         select e.id as id, 
        //         extract(year from e.joinDate) as join_year
        //         from Employee e
        // """;
        // Aqui se hace de manera mas corta
        String jpql = """
            select e.id as id, 
            year(e.joinDate) as join_year
            from Employee e
        """;
        
        session.createQuery(jpql, Tuple.class)
                .getResultList()
                .forEach(t -> System.out.println(t.get("id") + " - " + t.get("join_year")));
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var phones1 = new ArrayList<>(List.of("123-456-7890", "123-456-7891"));
        var employee1 = new Employee("John Doe", "employee1@email.com", "123 Main St.",
                1000.0, LocalDateTime.of(2022, 1, 1, 9, 0));
        employee1.setPhones(phones1);   

        var phones2 = new ArrayList<>(List.of("123-456-7892", "123-456-7893"));
        var employee2 = new Employee(null, "mike.app@email.com", "123 Main Street.",
                1500.0, LocalDateTime.of(2023, 1, 1, 9, 0));
        employee2.setPhones(phones2);   
        
        var phones3 = new ArrayList<>(List.of("987-654-3210", "987-654-3211"));
        var employee3 = new Employee("John Smith", "bob.app@email.com", "456 Main St.",
            2000.0, LocalDateTime.of(2024, 1, 1, 9, 0));
        employee3.setPhones(phones3);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);

        session.getTransaction().commit();
    }

}

package com.hibernate.model;

import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaJoin;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.Test;

import com.hibernate.HibernateUtil;
import com.hibernate.dto.*;

import jakarta.persistence.Tuple;

public class DtoTest {

    @Test
    void dtoClassInJpql() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select new com.hibernate.dto.EmployeeEmail(e.id, e.email) from Employee e
                """;
        session.createQuery(jpql, EmployeeEmail.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void dtoClassWithAssociationInJpql() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select new com.hibernate.dto.EmployeeCountry(e.id, e.address.country) 
                from Employee e
                """;
        session.createQuery(jpql, EmployeeCountry.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void tuples() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e.id, e.email
                from Employee e
                """;
        session.createQuery(jpql, Tuple.class)
                .getResultList()
                .forEach(tuple -> System.out.println("id: " + tuple.get(0) + ", email: " + tuple.get(1)));
    }

    // mismo metodo que el anterior pero con sql nativo
    @Test
    void tuplesSqlNative() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String sql = """
                select id, email
                from Employee e
                """;
        session.createNativeQuery(sql, Tuple.class)
                .getResultList()
                .forEach(tuple -> System.out.println("id: " + tuple.get("id") + ", email: " + tuple.get("email")));
    }

    @Test
    void criteria() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<EmployeeCountry> cq = cb.createQuery(EmployeeCountry.class);

        JpaRoot<Employee> root = cq.from(Employee.class);
        JpaJoin<Object, Object> address = root.join("address");

        cq.select(cb.construct(EmployeeCountry.class, 
                root.get("id"), address.get("country")));
        
        session.createQuery(cq)
                .getResultList()
                .forEach(System.out::println);
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var address1 = new Address("Street 1", "12345", "Country 1");
        var address2 = new Address("Street 2", "54321", "Country 2");
        var address3 = new Address("Street 3", "67890", "Country 3");

        var employee1 = new Employee("John", "Doe", "employee1@email.com", address1);
        var employee2 = new Employee("Jane", "Doe", "employee2@email.com", address2);
        var employee3 = new Employee("Jim", "Doe", "employee3@gmail.com", address3);

        session.persist(address1);
        session.persist(address2);
        session.persist(address3);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);

        session.getTransaction().commit();
    }

}

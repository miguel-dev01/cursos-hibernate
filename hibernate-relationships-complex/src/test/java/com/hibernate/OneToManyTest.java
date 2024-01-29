package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.onetomany.*;

public class OneToManyTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var company1 = session.find(Company.class, 1L);
        company1.getEmployees().forEach(System.out::println);

    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var company1 = new Company("12345678");
        var company2 = new Company("87654321");

        var employee1 = new Employee("John", company1);
        var employee2 = new Employee("Mary", company1);
        var employee3 = new Employee("Peter", company2);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);

        session.persist(company1);
        session.persist(company2);

        session.getTransaction().commit();
    }

}

package com.hibernate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.hibernate.model.CreditCard;
import com.hibernate.model.Employee;

public class CollectionsTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var employee1 = session.find(Employee.class, 1L);
        employee1.getPhones().forEach(System.out::println);
    }

    @Test
    void test2() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var employee3 = session.find(Employee.class, 3L);
        employee3.getSalaries().forEach(System.out::println);

        var employee5 = session.find(Employee.class, 5L);
        employee5.getPostalCodes().forEach(System.out::println);
    }

    @Test
    void test3() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var employee7 = session.find(Employee.class, 1L);
        employee7.getCreditCards().forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        var employee1 = new Employee("John Doe",
            "12345678Z", List.of("123456789", "987654321"));
        var employee2 = new Employee("Jane Doe",
            "87654321A", List.of("123456789", "987654321"));

        session.persist(employee1);
        session.persist(employee2);

        var employee3 = new Employee("John Doe",
                "12345678Z", List.of("123456789", "987654321"), List.of(1000.0, 2000.0));
        var employee4 = new Employee("Jane Doe",
                "87654321A", List.of("123456789", "987654321"), List.of(3000.0, 4000.0));

        session.persist(employee3);
        session.persist(employee4);

        var employee5 = new Employee("John Doe", "12345678Z",
                List.of("123456789", "987654321"),
                List.of(1000.0, 2000.0),
                Set.of("12345", "54321"));
        var employee6 = new Employee("Jane Doe", "87654321A",
                List.of("123456789", "987654321"),
                List.of(3000.0, 4000.0),
                Set.of("12345", "54321"));
        
        session.persist(employee5);
        session.persist(employee6);

        var c1 = new CreditCard("1111111111111111", "John Doe");
        var c2 = new CreditCard("2222222222222222", "Jane Doe");
        var c3 = new CreditCard("3333333333333333", "John Doe");

        session.persist(c1);
        session.persist(c2);
        session.persist(c3);

        var employee7 = new Employee("Michael", "5555554W",
                Map.of("111", c1,
                        "222", c2,
                        "333", c3));
            
        session.persist(employee7);

        session.beginTransaction().commit();
    }

}

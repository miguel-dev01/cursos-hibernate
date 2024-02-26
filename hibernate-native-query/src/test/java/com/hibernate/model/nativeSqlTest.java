package com.hibernate.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.StandardBasicTypes;
import org.junit.jupiter.api.Test;

import com.hibernate.HibernateUtil;

import jakarta.persistence.Tuple;

public class nativeSqlTest {

    @Test
    void findAll()  {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var sql = "SELECT * FROM Employee";
        session.createNativeQuery(sql, Employee.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void join()  {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var sql = """
                select e.id, e.salary, a.city 
                from Employee e left join Address a ON e.address_id = a.id
                """;
        session.createNativeQuery(sql, Object[].class)
                .getResultList()
                .forEach(e -> System.out.println("id: " + e[0] + ", salary: " + e[1] + ", city: " + e[2]));
    }

    @Test
    void addScalarTest() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        String sql = """
                    select e.id, e.email, e.bonus, e.city from employees e
                    """;
        List<Tuple> employees = session.createNativeQuery(sql, Tuple.class)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("email", StandardBasicTypes.STRING)
                .addScalar("bonus", StandardBasicTypes.DOUBLE)
                .addScalar("city", StandardBasicTypes.STRING)
                .getResultList();
        employees.forEach(e -> {
            System.out.println(e.get("id"));
            System.out.println(e.get("email"));
            System.out.println(e.get("bonus"));
            System.out.println(e.get("city"));
        });
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("calle1", "c1");
        var address2 = new Address("calle2", "c2");
        var address3 = new Address("calle3", "c3");
        session.persist(address1);
        session.persist(address2);
        session.persist(address3);

        var phones1 = new ArrayList<>(List.of("11", "22"));
        var emp1 = new Employee("e1@com", 1000.0, 100.0, "c1", "e1 e1", phones1,
                LocalDateTime.of(2021, 1, 1, 1, 1), address1);

        var phones2 = new ArrayList<>(List.of("11", "22"));
        var emp2 = new Employee("e2@com", 1000.0, 100.0, "c1", "e1 e1", phones2,
                LocalDateTime.of(2021, 1, 1, 1, 1), address2);

        var phones3 = new ArrayList<>(List.of("11", "22"));
        var emp3 = new Employee("e3@com", 1000.0, 100.0, "c1", "e1 e1", phones3,
                LocalDateTime.of(2021, 1, 1, 1, 1), address3);

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        session.getTransaction().commit();
    }

}

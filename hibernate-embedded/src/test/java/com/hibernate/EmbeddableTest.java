package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.example1.Address;
import com.hibernate.model.example1.Customer;
import com.hibernate.model.example1.Employee;

public class EmbeddableTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var c1 = session.find(Customer.class, 1L);
        System.out.println(c1);

    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("Calle Falsa1", "Madrid", "España");
        var address2 = new Address("Calle Falsa2", "Valencia", "España");

        var customer1 = new Customer("John", 32, address1);
        var customer2 = new Customer("Jane", 25, address2);

        var employee1 = new Employee("Maria", 30, address1);
        var employee2 = new Employee("Pedro", 40, address2);

        session.persist(customer1);
        session.persist(customer2);
        session.persist(employee1);
        session.persist(employee2);

        session.getTransaction().commit();

    }

}
